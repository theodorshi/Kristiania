import type { IVenue } from "../../interfaces/IVenue";
import { useContext, useEffect, useState } from "react";
import { VenueContext } from "../../contexts/VenueContext";
import type { IVenueContext } from "../../interfaces/IVenueContext";
import { useNavigate } from "react-router-dom";

const VenueDetailItem = () => {
  const { deleteVenue } = useContext(VenueContext) as IVenueContext;
  const [venue, setVenue] = useState<IVenue | null>(null);
  const navigate = useNavigate();

  const localhostImageUrl = "http://localhost:5077/images/"

  useEffect(() => {
    const getLocalStorage = localStorage.getItem("activeVenue");
    if (getLocalStorage) {
      setVenue(JSON.parse(getLocalStorage));
    }
  }, [])

  const deleteFromLocalStorage = () => {
    localStorage.removeItem("activeVenue")
  }

  const goBack = () => {
    navigate(-1)
  }

  const handleDelete = async () => {
    if (venue != null) {
      await deleteVenue(Number(venue.id));
      deleteFromLocalStorage();
      goBack();
    }
  }

  if (!venue) {
    return <p className="text-2xl text-center">Ingen stadio valgt</p>
  }
  return (
    <article className="flex flex-col justify-center items-center py-4">
      <h2 className="page-h1">{venue.name}</h2>
      <div className="flex flex-col justify-center items-start">
        <p className="text-small">Id: {venue.id}</p>
        <p className="text-medium">Kapasitet: {venue?.capacity}</p>
        <p className="text-medium">Leiepris: {venue?.rentPrice},- NOK</p>
      </div>
      <p className="text-medium p-4 md:w-[60%]">Beskrivelse: {venue.description}</p>
      <img className="rounded-lg" src={localhostImageUrl + venue?.image} alt={`Bildet av stadioen ${venue?.name}`} />
      <div className="py-4 flex gap-2 w-full justify-center">
        <button
          onClick={goBack}
          className="button default-btn w-[40%] md:w-[20%]"
        >Gå tilbake</button>
        <button onClick={handleDelete} className="button remove-btn  w-[40%] md:w-[20%]">Slett</button>
      </div>
    </article>);
};

export default VenueDetailItem;

