import { AthleteContext } from "../../contexts/AthleteContext";
import type { IAthleteContext } from "../../interfaces/IAthleteContext";
import { useRef, useContext, useState, type FormEvent } from "react";

const AthleteDelete = () => {

    const { deleteAthlete } = useContext(AthleteContext) as IAthleteContext;
    const [status, setStaus] = useState("");

    const idInput = useRef<HTMLInputElement | null>(null);

    const handleDeleteAthlete = async (e: FormEvent) => {
        e.preventDefault();
        const id = Number(idInput.current?.value);
        const response = await deleteAthlete(id)
        if (response.success) {
            setStaus("Spiller med " + id + " ble slettet");
            
        } else {
            setStaus("Id eksisterer ikke i databasen")
        }
    }

    return (
        <section className="add-box mb-8">
            <h3 className="text-3xl font-bold">Slett spiller</h3>
            <form className="add-box--form">
                <label className="add-box--form__label">Skriv inn id</label>
                <input className="add-box--form__input" type="number" ref={idInput} />
                <button className="button remove-btn" onClick={handleDeleteAthlete}>Slett spiller</button>
            </form>
            <p>{status}</p>
        </section>
    )
}

export default AthleteDelete;
