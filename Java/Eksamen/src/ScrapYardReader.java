import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScrapYardReader {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private final List<Vehicle> vehicleReadFromFile = new ArrayList<>();
    private final List<ScrapYard> scrapYardReadFromFile = new ArrayList<>();

    public List<Vehicle> getVehicleReadFromFile() {
        return vehicleReadFromFile;
    }

    public List<ScrapYard> getScrapYardReadFromFile() {
        return scrapYardReadFromFile;
    }

    public List<Vehicle> readFromFile() throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        int scrapYardCounter = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < scrapYardCounter; i++) {
            int scrapyardID = Integer.parseInt(scanner.nextLine());
            String name = scanner.nextLine();
            String address = scanner.nextLine();
            String phoneNumber = scanner.nextLine();
            ScrapYard scrapYard = new ScrapYard(scrapyardID, name, address, phoneNumber);
            scrapYardReadFromFile.add(scrapYard);
            scanner.nextLine();
        }

        int vehicleCounter = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < vehicleCounter; i++) {
            int vehicleId = Integer.parseInt(scanner.nextLine());
            int scrapYardId = Integer.parseInt(scanner.nextLine());
            String vehicleType = scanner.nextLine();
            String brand = scanner.nextLine();
            String model = scanner.nextLine();
            int yearModel = Integer.parseInt(scanner.nextLine());
            String registrationNumber = scanner.nextLine();
            String chassisNumber = scanner.nextLine();
            boolean driveable = Boolean.parseBoolean(scanner.nextLine());
            int numberOfSellableWheels = Integer.parseInt(scanner.nextLine());

            switch (vehicleType) {
                case "FossilCar" -> {
                    String fuelType = scanner.nextLine();
                    int fuelAmount = Integer.parseInt(scanner.nextLine());
                    FossilCar fossilCar = new FossilCar(vehicleId, scrapYardId, vehicleType, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, fuelType, fuelAmount);
                    vehicleReadFromFile.add(fossilCar);
                }
                case "ElectricCar" -> {
                    int batteryCapacity = Integer.parseInt(scanner.nextLine());
                    int chargeLevel = Integer.parseInt(scanner.nextLine());
                    ElectricCar electricCar = new ElectricCar(vehicleId, scrapYardId, vehicleType, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, batteryCapacity, chargeLevel);
                    vehicleReadFromFile.add(electricCar);
                }
                case "Motorcycle" -> {
                    boolean hasSidecar = Boolean.parseBoolean(scanner.nextLine());
                    int engineCapacity = Integer.parseInt(scanner.nextLine());
                    boolean isModified = Boolean.parseBoolean(scanner.nextLine());
                    int numberOfWheels = Integer.parseInt(scanner.nextLine());
                    Motorcycle motorcycle = new Motorcycle(vehicleId, scrapYardId, vehicleType, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, hasSidecar, engineCapacity, isModified, numberOfWheels);
                    vehicleReadFromFile.add(motorcycle);
                }
                default -> {
                    System.out.println("Warning: Unknown vehicle type: " + vehicleType);
                }
            }
            scanner.nextLine();
        }
        scanner.close();
        return vehicleReadFromFile;
    }
}