import { createContext, useEffect, useState, type ReactNode } from "react";
import type { IFinance } from "../interfaces/IFinance";
import type { IFinanceContext } from "../interfaces/IFinanceContext";
import FinanceService from "../services/FinanceService";
import type { IDefaultResponse, Props } from "../interfaces/ResponseInterfaces";

export const FinanceContext = createContext<IFinanceContext | null>(null);

export const FinanceProvider = ({ children }: Props) => {
    const [finance, setFinance] = useState<IFinance>(
        {
            id: 1,
            moneyLeft: 0,
            numberOfPurchases: 0,
            moneySpent: 0
        }
    );

    const setFinanceFromService = async () => {
        const response = await FinanceService.getFinance();
        if (response.success && response.data != null) {
            setFinance(
                response.data
            )
        }
    }

    useEffect(() => {
        setFinanceFromService()
    }, [])


    const updateFinance = async (finance: IFinance): Promise<IDefaultResponse> => {
        const response = await FinanceService.putFinance(finance);

        if (response.success) {
            setFinanceFromService();
        }

        return (response)
    }

    return (
        <FinanceContext.Provider value={{
            finance, updateFinance
        }}>
            {children}
        </FinanceContext.Provider>
    )
}