import { useState, useContext, useRef, type FormEvent, type ChangeEvent } from "react"
import { VenueContext } from "../../contexts/VenueContext";
import { type IVenueContext } from "../../interfaces/IVenueContext";
import { type IVenue } from "../../interfaces/IVenue";
import ImageUploadService from "../../services/ImageUploadService";


const VenueAdd = () => {
    const { saveVenue, getVenueQuantity } = useContext(VenueContext) as IVenueContext
    const [status, setStaus] = useState("");
    const [image, setImage] = useState<File>();

    const nameInput = useRef<HTMLInputElement | null>(null);
    const capacityInput = useRef<HTMLInputElement | null>(null);
    const rentPriceInput = useRef<HTMLInputElement | null>(null);
    const descriptionInput = useRef<HTMLTextAreaElement | null>(null);


    const imageHandler = (e: ChangeEvent<HTMLInputElement>) => {
        const { files } = e.target;

        if (files != null) {
            const file = files[0];
            setImage(file);
        }
    }

    const handleSaveVenue = async (e: FormEvent) => {
        e.preventDefault();
        if (
            nameInput.current && nameInput.current.value.trim() !== "" &&
            capacityInput.current && !isNaN(Number(capacityInput.current.value)) &&
            rentPriceInput.current && !isNaN(Number(rentPriceInput.current.value)) &&
            descriptionInput.current && descriptionInput.current.value.trim() !== "") {
            const filename = image?.name

            if (image != null) {
                const imageResponse = await ImageUploadService.uploadImage(image as File)
                if (imageResponse.success === false) {
                    setStaus("Bildet kunne ikke lastes opp")
                }
            }

            const newVenue: IVenue = {
                name: nameInput.current.value,
                capacity: Number(capacityInput.current?.value),
                rentPrice: Number(rentPriceInput.current?.value),
                description: descriptionInput.current.value,
                image: filename || "",
            }
            const response = await saveVenue(newVenue);

            if (response.success) {
                setStaus(`${newVenue.name} ble lagt til i databasen`);
                nameInput.current.value = "";
                capacityInput.current.value = "";
                rentPriceInput.current.value = "";
                descriptionInput.current.value = "";
            }
            console.log(filename, response)

            return response;
        } else {
            setStaus("Fyll inn gyldige verdier")
        }
    }
    return (
        <section className="add-box">
            <h3 className="add-box--header">Legg til ny stadio</h3>
            <p className="text-medium" >Antall stadioner: {getVenueQuantity()}</p>
            <form className="add-box--form">
                <label className="add-box--form__label">Navn</label>
                <input className="add-box--form__input" type="text" ref={nameInput} />

                <label className="add-box--form__label">Kapasitet</label>
                <input className="add-box--form__input" type="number" ref={capacityInput} />

                <label className="add-box--form__label">Leiepris (NOK)</label>
                <input className="add-box--form__input" type="number" ref={rentPriceInput} />

                <label className="add-box--form__label">Beskrivelse</label>
                <textarea className="add-box--form__input" ref={descriptionInput} rows={3}></textarea>

                <label className="add-box--form__label">Bilde</label>
                <input className="add-box--form__input" type="file" onChange={imageHandler} />

                <button className="button add-btn mt-8" onClick={handleSaveVenue}>Legg til</button>

                <p>{status}</p>
            </form>
        </section>
    )
}
export default VenueAdd;