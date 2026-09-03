import { createContext, useEffect, useState } from "react";
import type { IVenue } from "../interfaces/IVenue";
import type { IVenueContext } from "../interfaces/IVenueContext";
import VenueService from "../services/VenueService";
import type { IDefaultResponse, Props } from "../interfaces/ResponseInterfaces";

export const VenueContext = createContext<IVenueContext | null>(null);

export const VenueProvider = ({ children }: Props) => {
    const [venues, setVenue] = useState<IVenue[]>([]);

    const setVenueFromService = async () => {
        const response = await VenueService.getAllVenue();
        if (response.success == true && response.data != null) {
            setVenue(
                response.data
            );
        }
    }

    useEffect(() => {
        setVenueFromService()
    }, []);

    const getVenueQuantity = (): number => {
        return venues.length;
    }

    const saveVenue = async (venue: IVenue): Promise<IDefaultResponse> => {
        const response = await VenueService.postVenue(venue);

        if (response.success && response.data != null) {
            const newVenueWithId: IVenue = response.data;
            setVenue(
                prev => [newVenueWithId, ...prev]
            )
        }
        return response;
    }

    const deleteVenue = async (id: number): Promise<IDefaultResponse> => {
        const response = await VenueService.deleteVenue(id);
        setVenue(prev => prev.filter(v => v.id !== id))
        return response;
    }

    const editVenue = async (venue: IVenue): Promise<IDefaultResponse> => {
        const response = await VenueService.putVenue(venue);
        if (response.success) {
            setVenueFromService();
        }
        return response;
    }

    return (
        <VenueContext.Provider value={{
            venues, getVenueQuantity, saveVenue, deleteVenue, editVenue
        }}>
            {children}
        </VenueContext.Provider>
    )
}