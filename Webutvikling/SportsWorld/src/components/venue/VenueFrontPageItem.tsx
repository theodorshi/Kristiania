import { VenueContext } from "../../contexts/VenueContext";
import type { IVenueContext } from "../../interfaces/IVenueContext";
import type { IVenue } from "../../interfaces/IVenue";
import { useContext } from "react";


const VenueFrontPageItem = () => {
    const { venues } = useContext(VenueContext) as IVenueContext;
    const localhostImageUrl = "http://localhost:5077/images/"

    if (!venues || venues.length === 0) {
        return <p>Laster inn...</p>
    }

    let randomIndex = Math.floor(Math.random() * venues.length);
    let randomVenue: IVenue = venues[randomIndex];

    const showImageJSX = () => {
        let imageJSX;
        if (randomVenue.image === "") {
            imageJSX = <div className="image-responsive h-full box-color-dark  flex justify-center items-center">
                <p>Ingen bilde lagt til</p>
            </div>
        } else {
            imageJSX = <img className="image-responsive h-full" src={localhostImageUrl + randomVenue.image} alt={`Bilde av ${randomVenue.name}`} />
        }

        return imageJSX;
    }

    return (
        <article className="mt-4 p-8 flex flex-col justify-center items-center bg-blue-400 rounded-lg">
            <h3 className="text-large font-bold text-center">{randomVenue.name}</h3>
            <p className="text-medium ">Kapasitet: {randomVenue.capacity}</p>
            <div className="h-100 flex justify-center items-center p-8">
                {showImageJSX()}
            </div>
        </article>
    )
}
export default VenueFrontPageItem;