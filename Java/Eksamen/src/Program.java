import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Program {
    private final Scanner input = new Scanner(System.in);
    private final ScrapYardService scrapYardService;

    public Program() {
        scrapYardService = new ScrapYardService();
    }
    public void mainMenu() throws SQLException {
        String choice = "";
        System.out.println("Welcome to the scrape yard");
        while (!choice.equals("5")) {
            displayMainMenu();
            Scanner input = new Scanner(System.in);
            choice = input.nextLine().toLowerCase();
            switch (choice) {
                case "1" -> seeInfoAboutAllVehicles();
                case "2" -> seeTotalFuelAmountInFossilCars();
                case "3" -> seeInfoAboutDriveableVehicles();
                case "4" -> seeAmountOfVehiclesByType();
                case "5" -> quit();
                default -> System.out.println("Please provide a valid option");
            }
        }
    }

    private void quit() {
        System.out.println("Goodbye!");
    }

    private void seeAmountOfVehiclesByType() {
        try {
            scrapYardService.getVehicleAmountByType();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private void seeInfoAboutDriveableVehicles() {
        try {
            List<Vehicle> drivableVehicles = scrapYardService.getAllDriveableVehicles();
            if (drivableVehicles.isEmpty()) {
                System.out.println("No driveable vehicles found");
            } else {
                for (Vehicle vehicle : drivableVehicles) {
                    System.out.println(vehicle);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error" + e.getMessage());
        }
    }

    private void seeTotalFuelAmountInFossilCars() {
        try {
            scrapYardService.getTotalAmountOfFuel();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private void seeInfoAboutAllVehicles() throws SQLException {
        List<Vehicle> allVehicles = scrapYardService.getAllVehicles();
        System.out.println("Here are all the vehicles:");
        for (Vehicle vehicle : allVehicles) {
            System.out.println(vehicle);
        }
    }

    private void displayMainMenu() {
        System.out.println("Here are your options");
        System.out.println("1: See info about all vehicles:");
        System.out.println("2: See total fuel amount in the fossil cars:");
        System.out.println("3: See info about driveable vehicles:");
        System.out.println("4: See amount of vehicles by type:");
        System.out.println("5: Quit");
    }
}
