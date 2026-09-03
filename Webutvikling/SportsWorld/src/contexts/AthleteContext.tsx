import { createContext, useEffect, useState } from "react";
import type { IAthlete } from "../interfaces/IAthlete";
import type { IAthleteContext } from "../interfaces/IAthleteContext";
import AthleteService from "../services/AthleteService";
import type { IDefaultResponse, Props } from "../interfaces/ResponseInterfaces";


export const AthleteContext = createContext<IAthleteContext | null>(null);

export const AthleteProvider = ({ children }: Props) => {
    const [athletes, setAthlete] = useState<IAthlete[]>([]);

    const setAthleteFromService = async () => {
        const response = await AthleteService.getAllAthlete();
        if (response.success == true && response.data != null) {
            setAthlete(
                response.data.reverse()
            );
        }
    }

    useEffect(() => {
        setAthleteFromService()
    }, []);

    const getAthleteQuantity = (): number => {
        return athletes.length;
    }

    const saveAthlete = async (athlete: IAthlete): Promise<IDefaultResponse> => {
        const response = await AthleteService.postAthlete(athlete);

        if (response.success && response.data != null) {
            const newAthleteWithId: IAthlete = response.data;
            setAthlete(
                prev => [newAthleteWithId, ...prev]
            )
        }
        return response;
    }

    const deleteAthlete = async (id: number): Promise<IDefaultResponse> => {
        const response = await AthleteService.deleteAthlete(id);
        setAthlete(prev => prev.filter(a => a.id !== id))
        return response;
    }



    const editAthlete = async (athlete: IAthlete): Promise<IDefaultResponse> => {
        const response = await AthleteService.putAthlete(athlete);
        if (response.success) {
            setAthleteFromService();
        }
        return response;
    }

    return (
        <AthleteContext.Provider value={{
            athletes, getAthleteQuantity, saveAthlete, deleteAthlete, editAthlete
        }}>
            {children}
        </AthleteContext.Provider>

    )
}