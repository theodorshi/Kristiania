import { useState, useContext, useRef, type FormEvent, type ChangeEvent } from "react"
import { AthleteContext } from "../../contexts/AthleteContext"
import type { IAthleteContext } from "../../interfaces/IAthleteContext"
import { type IAthlete } from "../../interfaces/IAthlete"
import ImageUploadService from "../../services/ImageUploadService";

const AthleteAdd = () => {
    const { saveAthlete } = useContext(AthleteContext) as IAthleteContext
    const [status, setStaus] = useState("");
    const [image, setImage] = useState<File>();

    const nameInput = useRef<HTMLInputElement | null>(null);
    const genderInput = useRef<HTMLInputElement | null>(null);
    const priceInput = useRef<HTMLInputElement | null>(null);



    const imageHandler = (e: ChangeEvent<HTMLInputElement>) => {
        const { files } = e.target;

        if (files != null) {
            const file = files[0];
            setImage(file);
        }
    }


    const handleSaveAthlete = async (e: FormEvent) => {
        e.preventDefault();
        if (nameInput.current && nameInput.current.value.trim() != ""
            && genderInput.current && genderInput.current.value.trim() != ""
            && priceInput.current && priceInput.current.value.trim() != ""
            && !isNaN(Number(priceInput.current.value))
        ) {
            const filename = image?.name

            if (image != null) {
                const imageResponse = await ImageUploadService.uploadImage(image as File)
                if (imageResponse.success === false) {
                    setStaus("Bilde kunne ikke lastes opp")
                }
            }

            // Stor forbokstav på kjønn
            let genderUpperCase = genderInput.current.value.slice(0, 1).toUpperCase();
            let genderLowerCase = genderInput.current.value.slice(1).toLowerCase();
            let genderFormatted = genderUpperCase + genderLowerCase;

            const newAthletes: IAthlete = {
                name: nameInput.current.value,
                gender: genderFormatted,
                price: Number(priceInput.current?.value),
                image: filename || "",
                purchaseStatus: false
            }

            const response = await saveAthlete(newAthletes);

            if (response.success) {
                setStaus(`${newAthletes.name} ble lagt til i databasen`);
                nameInput.current.value = "";
                genderInput.current.value = "";
                priceInput.current.value = "";
            }
            console.log(filename, response)

            return response;
        } else {
            setStaus("Fyll inn gyldige verdier")
        }
    }

    return (
        <section className="add-box">
            <h3 className="add-box--header">Legg til ny potensiell atlet</h3>

            <form className="add-box--form">

                <label className="add-box--form__label">Navn</label>
                <input className="add-box--form__input" type="text" ref={nameInput} />


                <label className="add-box--form__label">Kjønn</label>
                <input className="add-box--form__input" type="text" ref={genderInput} />


                <label className="add-box--form__label">Pris (NOK)</label>
                <input className="add-box--form__input" type="text" ref={priceInput} />



                <label className="add-box--form__label">Bilde</label>
                <input className="add-box--form__input" type="file" onChange={imageHandler} />


                <button className="button add-btn mt-8" onClick={handleSaveAthlete}>Legg til</button>

                <p className="text-center">{status}</p>

            </form>
        </section>
    )
}

export default AthleteAdd;