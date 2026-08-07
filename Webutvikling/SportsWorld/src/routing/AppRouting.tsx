import { BrowserRouter, Route, Routes } from "react-router-dom";
import { HomePage, AddPotentialAtheletePage, AthleteManegerPage, FinanceDashboardPage, VenuePage, VenueDetailPage, NotFoundPage, } from "../pages";
import MainHeader from "../components/shared/MainHeader";
import MainFooter from "../components/shared/MainFooter";

const AppRouting = () => {
  return (
    <BrowserRouter>
      <div className="min-h-screen flex flex-col">
        <MainHeader />

        <main className="flex-1">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="add-potential-athelete" element={<AddPotentialAtheletePage />} />
            <Route path="athlete-manager" element={<AthleteManegerPage />} />
            <Route path="finance-dashboard" element={<FinanceDashboardPage />} />
            <Route path="venue" element={<VenuePage />} />
            <Route path="venue/detail" element={<VenueDetailPage />} />
            <Route path="*" element={<NotFoundPage />} />
          </Routes>
        </main>

        <MainFooter />
      </div>
    </BrowserRouter>
  );
};

export default AppRouting;
