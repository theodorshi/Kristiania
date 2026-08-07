import { type IFinance } from "./IFinance";
import { type IDefaultResponse } from "./ResponseInterfaces";

export interface IFinanceContext{
    finance: IFinance
    updateFinance: (finance: IFinance) => Promise<IDefaultResponse>
}