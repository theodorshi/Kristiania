import { type IAthlete } from "../../interfaces/IAthlete";
import { useState, useRef, useContext, type FormEvent, type ChangeEvent } from "react";
import { AthleteContext } from "../../contexts/AthleteContext";
import { type IAthleteContext } from "../../interfaces/IAthleteContext";
import { FinanceContext } from "../../contexts/FinanceContext";
import type { IFinanceContext } from "../../interfaces/IFinanceContext";
import type { IFinance } from "../../interfaces/IFinance";
import ImageUploadService from "../../services/ImageUploadService"

const AthleteItem = ({ athlete }: { athlete: IAthlete }) => {
  const { editAthlete, deleteAthlete } = useContext(AthleteContext) as IAthleteContext;

  const { finance, updateFinance } = useContext(FinanceContext) as IFinanceContext;
  const [status, setStatus] = useState("");
  const [itemEdit, setItemEdit] = useState(false);
  const [image, setImage] = useState<File>();

  const localhostImageUrl = "http://localhost:5077/images/"

  const imageHandler = (e: ChangeEvent<HTMLInputElement>) => {
    const { files } = e.target;
    if (files != null) {
      const file = files[0];
      setImage(file);
    }
  }


  const nameInput = useRef<HTMLInputElement | null>(null);
  const genderInput = useRef<HTMLInputElement | null>(null);
  const priceInput = useRef<HTMLInputElement | null>(null);
  // const imageInput = useRef<HTMLInputElement | null>(null);

  const handleEditAthlete = async () => {
    if (nameInput.current && nameInput.current.value.trim() != ""
      && genderInput.current && genderInput.current.value.trim() != ""
      && priceInput.current && priceInput.current.value.trim() != ""
      && !isNaN(Number(priceInput.current.value))
    ) {

      const currentImg = athlete.image;
      let filename;

      if (image != null) {
        const imageResponse = await ImageUploadService.uploadImage(image as File)
        if (imageResponse.success === false) {
          setStatus("Bilde kunne ikke lastes opp")
        } else {
          filename = image.name;
        }
      } else {
        filename = currentImg;
      }

      // Stor forbokstav på kjønn
      let genderUpperCase = genderInput.current.value.slice(0, 1).toUpperCase();
      let genderLowerCase = genderInput.current.value.slice(1).toLowerCase();
      let genderFormatted = genderUpperCase + genderLowerCase;


      const edietAthletes: IAthlete = {
        id: athlete.id,
        name: nameInput.current.value,
        gender: genderFormatted,
        price: Number(priceInput.current?.value),
        image: filename || "",
        purchaseStatus: athlete.purchaseStatus
      }


      const response = await editAthlete(edietAthletes);

      if (response.success) {
        setStatus("");
        nameInput.current.value = "";
        genderInput.current.value = "";
        priceInput.current.value = "";
        setItemEdit(false);
      }
      return response;
    } else {
      setStatus("Fyll inn gyldige verdier");
    }
  }

  const handleDeleteAthlete = async () => {
    const response = await deleteAthlete(Number(athlete.id))
  }

  const handleSaleAthlete = async () => {
    const salePrice = athlete.price;

    const response = await deleteAthlete(Number(athlete.id))
    if (response.success) {

      const updatedFinance: IFinance = {
        id: finance.id,
        moneyLeft: finance.moneyLeft + salePrice,
        numberOfPurchases: finance.numberOfPurchases,
        moneySpent: finance.moneySpent
      }
      const updateResponse = await updateFinance(updatedFinance)
    }
  }

  const checkPurcasesStatus = () => {
    if (athlete.purchaseStatus) {
      handleSaleAthlete()
    } else {
      handleDeleteAthlete()
    }
  }

  const showSalableJSX = () => {
    let buttonText;
    if (athlete.purchaseStatus) {
      buttonText = "Selg";
    } else {
      buttonText = "Slett";
    }
    return buttonText;
  }


  //Skal bilde vises?
  const showImageJSX = () => {
    let imageJSX;
    if (athlete.image === "") {
      imageJSX = <div className="image-responsive h-full bg-[rgb(242,242,242)] flex justify-center items-center">
        <p>Ingen bilde lagt til</p>
      </div>
    } else {
      imageJSX = <img className="image-responsive h-full" src={localhostImageUrl + athlete.image} alt={`bildet av ${athlete.name}`} />
    }

    return imageJSX;
  }

  const showItemJSX = () => {

    if (itemEdit == false) {

      return (
        <article className="item-grid item">
          <h3 className="text-center text-3xl">
            {athlete.name}
          </h3>
          <p>Id: {athlete.id}, {athlete.gender}</p>
          <div className="h-80 w-full flex justify-center items-center">
            {showImageJSX()}
          </div>
          <p className="text-center text-2xl">Pris: {athlete.price},- NOK</p>
          <p className="text-center text-2xl font-bold">Status: {athlete.purchaseStatus ? "Kjøpt" : "Ikke kjøpt"}</p>


          <button className="button default-btn w-40"
            onClick={() => setItemEdit(true)}>Rediger</button>
          <button className={
            athlete.purchaseStatus
              ? "button add-btn w-40"
              : "button remove-btn w-40"
          } onClick={checkPurcasesStatus}>{showSalableJSX()}</button>
        </article>
      )
    } else {
      return (
        <article className="item-grid item">
          <div>
            <label className="add-box--form__label">Navn: </label>
            <input className="add-box--form__input w-[60%]"
              type="text"
              defaultValue={athlete.name}
              ref={nameInput}
              placeholder="Navn" />
          </div>

          <div>
            <label className="add-box--form__label">Kjønn: </label>
            <input className="add-box--form__input w-[60%]"
              type="text"
              defaultValue={athlete.gender}
              ref={genderInput}
              placeholder="Kjønn" />
          </div>

          <div>
            <label className="add-box--form__label">Pris(NOK): </label>
            <input className="add-box--form__input w-[45%]"
              type="number"
              defaultValue={athlete.price}
              ref={priceInput}
              placeholder="Pris" />
          </div>

          <div className="h-80 flex justify-center items-center">
            {showImageJSX()}
          </div>

          <div className="w-full">
            <label className="add-box--form__label">Last opp nytt bilde: </label>
            <input className="add-box--form__input w-full"
              type="file"
              placeholder="Bilde"
              onChange={imageHandler} />
          </div>

          <button className="button add-btn" onClick={async (e: FormEvent) => {
            e.preventDefault();
            handleEditAthlete();
          }}>Lagre endring</button>
          <p>{status}</p>
        </article>
      )

    }

  }

  return showItemJSX();
};

export default AthleteItem;