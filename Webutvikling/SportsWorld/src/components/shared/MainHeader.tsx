import { Link } from "react-router-dom";

const MainHeader = () => {
  return (
    <header className="bg-[rgb(0,30,70)] p-4  text-white">

      <div className="flex flex-col m-4 gap-4 items-center md:flex-row md:items-center md:justify-between" >
        <Link className="w-60 mx-auto md:w-60 md:mx-0 lg:w-80" to="/"> <img src="/sportsworldlogo.png" alt="Logo til sportsworld" /></Link>
      <nav>
          <ul className="flex flex-col w-50 gap-2 md:w-full md:flex-row md:gap-2 text-center">
              <li className="p-2 border border-white rounded"><Link to="/">Hjem</Link></li>
              <li className="p-2 border border-white rounded"><Link to="add-potential-athelete">Legg til</Link></li>
              <li className="p-2 border border-white rounded"><Link to="athlete-manager">Se og rediger</Link></li>
              <li className="p-2 border border-white rounded"><Link to="finance-dashboard">Økonomi</Link></li>
              <li className="p-2 border border-white rounded"><Link   to="venue">Stadio</Link></li>
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default MainHeader;
