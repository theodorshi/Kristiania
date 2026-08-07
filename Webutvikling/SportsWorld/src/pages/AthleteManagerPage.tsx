import AthleteDelete from "../components/athletes/AthleteDelete";
import AthleteSearch from "../components/athletes/AthleteSearch";
const AthleteManagerPage = () => {
  return (
    <section className="page flex flex-col justif-center align-center">
      <h1 className="page-h1">Athelete manager</h1>
      <AthleteDelete/>
      <AthleteSearch />
    </section>
  );
};

export default AthleteManagerPage;
