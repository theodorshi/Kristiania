import { useContext } from "react";
import VenueItem from "./VenueItem";
import type { IVenueContext } from "../../interfaces/IVenueContext";
import { VenueContext } from "../../contexts/VenueContext";

const VenueList = () => {
  const { venues } = useContext(VenueContext) as IVenueContext;

  const getVenueJSX = () => {
    const venueJSX = venues.map((venue, index) => {
      return <VenueItem key={"Venue" + index} venue={venue} />;
    });
    return venueJSX;
  };

  return (
    <>
      <section className="list-grid my-12">{getVenueJSX()}</section>
    </>
  );
};

export default VenueList;
