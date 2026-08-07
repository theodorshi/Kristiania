import type { IVenue } from "./IVenue"
import type { IDefaultResponse } from "./ResponseInterfaces"

export interface IVenueContext {
    venues: IVenue[],
    saveVenue: (venue: IVenue) => Promise<IDefaultResponse>
    getVenueQuantity: () => number
    deleteVenue: (id: number) => Promise<IDefaultResponse>
    editVenue: (venue: IVenue) => Promise<IDefaultResponse>

}