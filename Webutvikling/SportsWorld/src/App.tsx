import "./App.css";
import { AthleteProvider } from "./contexts/AthleteContext";
import { FinanceProvider } from "./contexts/FinanceContext";
import { VenueProvider } from "./contexts/VenueContext";
import AppRouting from "./routing/AppRouting";

function App() {

  return (
    <>
      <VenueProvider>
      <FinanceProvider>
      <AthleteProvider>
        <AppRouting />
      </AthleteProvider>
      </FinanceProvider>
      </VenueProvider>
      
    </>
  );
}

export default App;
