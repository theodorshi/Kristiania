import type { IVenue } from "../../interfaces/IVenue";
import { Link } from "react-router-dom";

const VenueItem = ({ venue }: { venue: IVenue }) => {
  const localhostImageUrl = "http://localhost:5077/images/"

  const saveToLocalStorage = () => {
    localStorage.setItem("activeVenue", JSON.stringify(venue))
  }

  return (
    <article className="item-grid item">
      <h3 className="font-bold text-3xl lg:text-2xl">
        {venue.name}
      </h3>
      <p>Id: {venue.id}</p>
      <div className="w-full md:h-60 lg:h-50 flex justify-center">
      <img className="image-responsive h-full" src={localhostImageUrl + venue.image} alt={`Bilde av ${venue.name}`} />
      </div>
      <button className="button default-btn"
      ><Link to="detail"
        onClick={saveToLocalStorage}>Se mer</Link></button>
    </article>
  );
};

export default VenueItem;

