import AthleteAdd from "../components/athletes/AthleteAdd";
import AddedPotentialAthleteList from "../components/athletes/AddedPotentialAthleteList";

const AddPotentialAtheletePage = () => {
  return (
    <section className="page">
      <h1 className="page-h1">Pontentielle spillere</h1>
      <AthleteAdd />
      <AddedPotentialAthleteList/>
    </section>
  );
};

export default AddPotentialAtheletePage;
