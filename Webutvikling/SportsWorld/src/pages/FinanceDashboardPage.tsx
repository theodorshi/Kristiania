import FinanceItem from "../components/finance/FinanceItem";
import FinanceLoan from "../components/finance/FinanceLoan";
import PotentialAthleteList from "../components/athletes/PotentialAthletesList";

const FinanceDashboardPage = () => {
  return (
    <section className="page">
       <h1 className="page-h1">Økonomi</h1>
      
      <div className="flex flex-col md:flex-row gap-4 max-w-[1268px] mx-auto">
      <section className="flex-1">
      <FinanceItem/>

      </section>

      <section className="flex-1">
        <FinanceLoan/>
        </section>
      </div>
      <section>
        <PotentialAthleteList/>
      </section>
    </section>
  );
};

export default FinanceDashboardPage;
