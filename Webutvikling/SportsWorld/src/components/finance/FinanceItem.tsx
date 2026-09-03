import { type IFinanceContext } from "../../interfaces/IFinanceContext";
import { FinanceContext } from "../../contexts/FinanceContext";
import { useContext } from "react";

const FinanceItem = () => {
  const { finance } = useContext(FinanceContext) as IFinanceContext

  return (
    <article className="bg-green-100 rounded p-4 h-full shadow flex flex-col justify-start items-start">
      <h3 className="text-3xl font-700 font-bold text-center pb-2">Bedriftens penger</h3>
      <p className="text-2xl font-700">Saldo: {finance.moneyLeft},- NOK</p>
      <p className="text-2xl font-700">Antall kjøp: {finance.numberOfPurchases}</p>
      <p className="text-2xl font-700">Penger brukt: {finance.moneySpent},-NOK</p>
    </article>
  );
};

export default FinanceItem;

