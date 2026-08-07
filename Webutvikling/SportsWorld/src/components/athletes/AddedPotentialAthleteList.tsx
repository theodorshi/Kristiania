import type { IAthlete } from "../../interfaces/IAthlete";
import { useContext } from "react";
import { AthleteContext } from "../../contexts/AthleteContext";
import type { IAthleteContext } from "../../interfaces/IAthleteContext";

const AddedPotentialAthleteList = () => {
    const { athletes } = useContext(AthleteContext) as IAthleteContext;

    const localhostImageUrl = "http://localhost:5077/images/";

    const filteredAthletes = athletes.filter(a => a.purchaseStatus == false);

    const showImageJSX = (athlete: IAthlete) => {
        let imageJSX;
        if (athlete.image === "") {
            imageJSX = <div className="h-full rounded-lg  box-color-dark flex justify-center items-center">
                <p className="text-center text-white text-2xl">Ingen bilde lagt til</p>
            </div>
        } else {
            imageJSX = <img className="image-responsive h-full" src={localhostImageUrl + athlete.image} alt={`bildet av ${athlete.name}`} />
        }

        return imageJSX;
    }

    const getAthleteJSX = () => {
        const filteredAthleteJSX = filteredAthletes.map((athlete, index) => {
            return (
                <article key={"Athlete" + index} className="item-grid flex flex-col gap-3 py-2 box-color-light shadow-grey-300 justify-center items-center">
                    <h3 className="text-center text-3xl">
                        {athlete.name}
                    </h3>
                    <p className="text-color-muted">id: {athlete.id}, {athlete.gender}</p>
                    <div className="h-80 w-full flex justify-center">
                        {showImageJSX(athlete)}
                    </div>
                    <p className="text-center text-2xl font-bold">Pris: {athlete.price},- NOK</p>
                </article>
            );
        });
        return filteredAthleteJSX;
    };


    return (
        <section className="flex flex-col pt-4">
            <p className="text-center py-8 text-large">Antall potensielle fotballspillere: {filteredAthletes.length}</p>
            <div className="list-grid">
                {getAthleteJSX()}
            </div>
        </section>
    )
};

export default AddedPotentialAthleteList;
