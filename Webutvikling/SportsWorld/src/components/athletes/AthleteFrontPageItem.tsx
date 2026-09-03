import { AthleteContext } from "../../contexts/AthleteContext";
import { type IAthleteContext } from "../../interfaces/IAthleteContext";
import { type IAthlete } from "../../interfaces/IAthlete";
import { useContext } from "react";

const AthleteFrontPageItem = () => {
  const { athletes } = useContext(AthleteContext) as IAthleteContext;

  const localhostImageUrl = "http://localhost:5077/images/"

  if (!athletes || athletes.length === 0) {
    return <p>Laster inn...</p>
  }

  const boughtAthletes = athletes.filter(a => a.purchaseStatus === true)

  if (boughtAthletes.length === 0) {
    return (
      <article className="mt-4 p-8 flex flex-col justify-center items-center bg-orange-400 rounded-lg">
        <h3 className="text-large text-white font-bold">Ingen spiller</h3>
        <p className="text-medium text-white text-white-muted ">Kjøp spillere</p>
        <div className="h-100 w-full flex justify-center items-center p-8">
        </div>
      </article>
    )
  }

  // Henter tilfeldig kjøpt spiller for å simulere ukens spiller
  let randomIndex = Math.floor(Math.random() * boughtAthletes.length);
  let randomAthlete: IAthlete = boughtAthletes[randomIndex];

  const showImageJSX = () => {
    let imageJSX;
    if (randomAthlete.image === "") {
      imageJSX = <div className="image-responsive md:w-[10%] h-full box-color-dark flex justify-center items-center">
        <p>Ingen bilde lagt til</p>
      </div>
    } else {
      imageJSX = <img className="image-responsive h-full" src={localhostImageUrl + randomAthlete.image} alt={`bildet av ${randomAthlete.name}`} />
    }

    return imageJSX;
  }

  return (

    <article className="mt-4 p-8 flex flex-col justify-center items-center bg-orange-400 rounded-lg">
      <h3 className="text-large  font-bold">{randomAthlete.name}</h3>
      <p className="text-medium  text-white-muted ">{randomAthlete.gender}</p>
      <div className="h-100 w-full flex justify-center items-center p-8">
        {showImageJSX()}
      </div>
    </article>
  )
}

export default AthleteFrontPageItem;