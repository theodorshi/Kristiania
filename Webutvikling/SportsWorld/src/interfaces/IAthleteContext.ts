import { type IAthlete } from "./IAthlete";
import { type IDefaultResponse } from "./ResponseInterfaces";

export interface IAthleteContext {
    athletes: IAthlete[]
    saveAthlete: (athlete: IAthlete) => Promise<IDefaultResponse>
    getAthleteQuantity: () => number
    deleteAthlete: (id: number) => Promise<IDefaultResponse>
    editAthlete: (athlete: IAthlete) => Promise<IDefaultResponse>
}