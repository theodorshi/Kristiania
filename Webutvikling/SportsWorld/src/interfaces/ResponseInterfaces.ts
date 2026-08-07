import type { IAthlete } from "./IAthlete"
import type { IVenue } from "./IVenue"
import type { IFinance } from "./IFinance"
import { type ReactNode } from "react"

//Default
export interface IDefaultResponse {
    success: boolean
}

//Athlete
export interface IAthleteResponse {
    success: boolean,
    data: IAthlete | null
}

export interface IAthletesResponse {
    success: boolean,
    data: IAthlete[] | null
}

//Venue
export interface IVenueResponse {
    success: boolean,
    data: IVenue | null
}

export interface IVenuesResponse {
    success: boolean,
    data: IVenue[] | null
}

// Finance
export interface IFinanceResponse {
    success: boolean,
    data: IFinance | null
}

export interface Props { children: ReactNode };

