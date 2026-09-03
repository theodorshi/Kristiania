import { useContext } from "react";
import AthleteItem from "./AthleteItem";
import { AthleteContext } from "../../contexts/AthleteContext";
import { type IAthleteContext } from "../../interfaces/IAthleteContext";

const AthleteList = () => {
  const { athletes } = useContext(AthleteContext) as IAthleteContext;

  const getAthleteJSX = () => {
    const athleteJSX = athletes.map((athlete, index) => {
      return <AthleteItem key={"Athlete" + index} athlete={athlete} />;
    });
    return athleteJSX;
  };

  return (
    <>
      <section className="list-grid">{getAthleteJSX()}</section>
    </>
  );
};

export default AthleteList;
