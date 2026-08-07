import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class ScrapYardImporter {
    private final ScrapYardReader scrapYardReader;
    private final ScrapYardService scrapYardService;

    public ScrapYardImporter() {
        this.scrapYardReader = new ScrapYardReader();
        scrapYardReader.setFileName("files/vehicle.txt");
        this.scrapYardService = new ScrapYardService();
    }

    public void importFile() throws FileNotFoundException, SQLException {
        scrapYardReader.readFromFile();

        List<Vehicle> vehicles = scrapYardReader.getVehicleReadFromFile();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found");
            return;
        }
        List<ScrapYard> scrapYards = scrapYardReader.getScrapYardReadFromFile();
        for (ScrapYard scrapYard : scrapYards) {
            scrapYardService.insertScrapeYard(scrapYard);
        }


        System.out.println("Inserting vehicles");
        scrapYardService.insertAllVehicles(vehicles);
    }
}

