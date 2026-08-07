import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Del 1 - Importer data fra fil

        /*
        try {
            ScrapYardImporter scrapYardImporter = new ScrapYardImporter();
            scrapYardImporter.importFile();
            System.out.println("File import completed");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

         */

        //Del 2 - Kjøre programmet
        try {
            Program program = new Program();
            program.mainMenu();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}