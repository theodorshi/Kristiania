import type { IFinanceContext } from "../../interfaces/IFinanceContext";
import { FinanceContext } from "../../contexts/FinanceContext";
import { useContext, useState, type FormEvent } from "react";
import { useRef } from "react";
import { type IFinance } from "../../interfaces/IFinance";

const FinanceLoan = () => {
    const { finance, updateFinance } = useContext(FinanceContext) as IFinanceContext;
    const [status, setStatus] = useState("");
    const loanAmountInput = useRef<HTMLInputElement | null>(null);


    // Kan få startlån på 10k dersom man har mindre enn 1000kr
    const getStartLoan = async () => {
        if (finance.moneyLeft < 1000) {
            const defaultFinance: IFinance = {
                id: finance.id,
                moneyLeft: finance.moneyLeft + 10000,
                numberOfPurchases: finance.numberOfPurchases,
                moneySpent: finance.moneySpent
            };
            await updateFinance(defaultFinance);
            setStatus("Du fikk et startlån på 10 000 kr")
        }
    }

    const handleUpdateFinance = async (e: FormEvent) => {
        e.preventDefault();

        if (loanAmountInput.current && loanAmountInput.current.value.trim() != "") {
            const loanAmountParsed = Number(loanAmountInput.current.value);
            if (loanAmountParsed! > (finance.moneyLeft * 0.2)) {
                setStatus("Beløpet er for høyt. Du kan maks låne 20% av beløpet du har på konto")
            } else {
                const newFinance: IFinance = {
                    id: finance.id,
                    moneyLeft: finance.moneyLeft + loanAmountParsed,
                    numberOfPurchases: finance.numberOfPurchases,
                    moneySpent: finance.moneySpent

                };
                const response = await updateFinance(newFinance);
                setStatus(`Lån innvliget. Du fikk ${loanAmountParsed} kr`)
                return response;
            }
        } else {
            setStatus("Skriv inn et gyldig tall")
        }
    }
    return (
        <section className="bg-blue-100 rounded p-4 shadow flex flex-col">
            <h3 className="text-3xl font-700 font-bold ">Lån fra banken</h3>

            <label className="add-box--form__label">Skriv inn ønsket lånebeløp</label>

            <input className="add-box--form__input " ref={loanAmountInput} type="number" />

            <p className=""><i>(Maks 20% av saldo)</i></p>

            <div className="flex gap-4 justify-center py-2">

                <button className="button add-btn" onClick={handleUpdateFinance}>Søk lån</button>

                <button className="button add-btn" onClick={getStartLoan}>Få startlån</button>
            </div>
            <p>{status}</p>
        </section>
    )
}

export default FinanceLoan;
