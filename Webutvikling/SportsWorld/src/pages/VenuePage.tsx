import VenueAdd from "../components/venue/VenueAdd";
import VenueList from "../components/venue/VenueList";

const VenuePage = () => {
  return (
    <section className="page">
      <h1 className="page-h1">Stadioner</h1>
      <VenueAdd />
      <VenueList/>
    </section>
  );
};

export default VenuePage;

