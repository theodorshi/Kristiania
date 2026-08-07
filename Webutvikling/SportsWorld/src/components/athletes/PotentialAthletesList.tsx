import { useContext } from "react";
import PotentialAthleteItem from "./PotentialAthleteItem";
import { AthleteContext } from "../../contexts/AthleteContext";
import { type IAthleteContext } from "../../interfaces/IAthleteContext";



const PotentialAthleteList = () => {
    const { athletes } = useContext(AthleteContext) as IAthleteContext;

    const filteredAthletes = athletes.filter(a => a.purchaseStatus == false);

    const getAthleteJSX = () => {
        const filteredAthleteJSX = filteredAthletes.map((athlete, index) => {
            return <PotentialAthleteItem key={"Athlete" + index} athlete={athlete} />;
        });
        return filteredAthleteJSX;
    };
    return (
        <section className="flex flex-col justify-center items-center">
            <h3 className="text-center text-3xl py-2">Spillere som kan kjøpes </h3>
            <p className="text-center text-medium pb-4">Antall: {filteredAthletes.length}</p>
            <section className="list-grid">
                {getAthleteJSX()}
            </section>
        </section>
    )
}

export default PotentialAthleteList;