import { useState, useContext } from "react";
import { AthleteContext } from "../../contexts/AthleteContext"
import type { IAthleteContext } from "../../interfaces/IAthleteContext";
import AthleteItem from "./AthleteItem";
import AthleteList from "./AthleteList";

const AthleteSearch = () => {
    const { athletes } = useContext(AthleteContext) as IAthleteContext;

    const [search, setSearch] = useState("");

    const getSearchResultJSX = () => {
        if (search.trim() === "") {
            return <AthleteList />
        } else {
            const filtered = athletes.filter(a => a.name.toLowerCase().includes(search.toLowerCase()));

            if (filtered.length === 0) {
                return (
                    <p className="text-2xl text-center">Fant ingen på søk</p>
                )
            }
            else {
                return (
                    <ul className="list-grid">
                        {filtered.map(a => (
                            <li className="col-span-12 md:col-span-6 lg:col-span-4 xl:col-span-3" key={a.id}>
                                <AthleteItem athlete={a} />
                            </li>
                        ))}
                    </ul>
                )
            }
        }
    }

    return (
        <>
            <section className="add-box">
                <h3 className="text-3xl font-bold">Søk etter spiller</h3>
                <form className="add-box--form">
                    <label className="add-box--form__label">Søk på navn</label>
                    <input
                        className="add-box--form__input"
                        type="text"
                        value={search}
                        onChange={e => setSearch(e.target.value)} />
                </form>
            </section>
            <section className="py-12">{getSearchResultJSX()}</section>
        </>
    )
}

export default AthleteSearch;