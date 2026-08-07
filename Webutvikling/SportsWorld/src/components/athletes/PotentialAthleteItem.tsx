import { useContext } from "react";
import { type IAthlete } from "../../interfaces/IAthlete";
import { AthleteContext } from "../../contexts/AthleteContext";
import type { IAthleteContext } from "../../interfaces/IAthleteContext";
import { FinanceContext } from "../../contexts/FinanceContext";
import type { IFinanceContext } from "../../interfaces/IFinanceContext";
import type { IFinance } from "../../interfaces/IFinance";
import { useState } from "react";

const PotentialAthleteItem = ({ athlete }: { athlete: IAthlete }) => {
  const { editAthlete } = useContext(AthleteContext) as IAthleteContext;
  const { finance, updateFinance } = useContext(FinanceContext) as IFinanceContext;
  const [status, setStatus] = useState("");

  const localhostImageUrl = "http://localhost:5077/images/"

  const handleEditAthlete = () => {
    if (finance.moneyLeft >= athlete.price) {

      const editedAthlete: IAthlete = {
        id: athlete.id,
        name: athlete.name,
        gender: athlete.gender,
        price: athlete.price,
        image: athlete.image,
        purchaseStatus: true
      }
      const response = editAthlete(editedAthlete);

      const updatedFinance: IFinance = {
        id: 1,
        moneyLeft: finance.moneyLeft - editedAthlete.price,
        numberOfPurchases: finance.numberOfPurchases + 1,
        moneySpent: finance.moneySpent + editedAthlete.price
      }
      const financeResponse = updateFinance(updatedFinance);
    } else {
      setStatus("Du har ikke nok penger til å kjøpe spilleren");
    }
  }

  //Skal bilde vises?
  const showImageJSX = () => {
    let imageJSX;
    if (athlete.image === "") {
      imageJSX = <div className="image-responsive h-full bg-blue-600 flex justify-center items-center">
        <p>Ingen bilde lagt til</p>
      </div>
    } else {
      imageJSX = <img className="image-responsive h-full" src={localhostImageUrl + athlete.image} alt={`bildet av ${athlete.name}`} />
    }
    return imageJSX;
  }

  return (
    <article className="item-grid item">
      <h3 className="text-center text-3xl font-bold">
        {athlete.name}
      </h3>
      <p>Id: {athlete.id}, Kjønn: {athlete.gender}</p>
      <div className="h-80 w-full flex justify-center items-center">
        {showImageJSX()}
      </div>
      <p className="text-center text-2xl font-bold">Pris: {athlete.price},- NOK</p>
      <p className="text-center text-2xl ">Status: {athlete.purchaseStatus ? "Kjøpt" : "Ikke kjøpt"}</p>
      <button className="button add-btn" onClick={handleEditAthlete}>Kjøp spiller</button>
      <p>{status}</p>
    </article>
  );
};

export default PotentialAthleteItem;
