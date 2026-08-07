import AthleteFrontPageItem from "../components/athletes/AthleteFrontPageItem";
import VenueFrontPageItem from "../components/venue/VenueFrontPageItem";

const HomePage = () => {
  return (
    <section className="flex flex-col items-center">
      <h1 className="page-h1">SportsWorld</h1>
      <h3 className="text-large">Fotballavdeling</h3>
      <p className="text-2xl px-8 max-w-[95%] md:max-w-[90%] lg:max-w-[80%]">På denne nettsiden kan du administrere fotballspillere og økonomi for Sportsworld sin fotballavdeling. Du kan legge til nye spillere, se detaljer, redigere informasjon og laste opp bilder.  Du kan også kjøpe og selge spillere. I tilleg kan du registrere stadioner og få oversikt over kostnad for leie.  </p>
      <div className="w-full flex flex-col justify-center items-center md:flex-row gap-4 md:gap-8 pt-12 px-8">
        <div className="flex-1">
          <h3 className="text-large text-center">Ukens spiller</h3>
          <AthleteFrontPageItem />
        </div>
        <div className="flex-1">
          <h3 className="text-large text-center">Ukens arrangement</h3>
          <VenueFrontPageItem />
        </div>
      </div>
    </section>
  );
};

export default HomePage;
