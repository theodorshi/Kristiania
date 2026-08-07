import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScrapYardService {
    private final MysqlDataSource scrapYardDS;

    public static final String ADD_SCRAPE_YARDS_SQL = "insert into Scrapyard values (?,?,?,?)";
    public static final String ADD_FOSSIL_CAR_SQL = "insert into FossilCar values (?,?,?,?,?,?,?,?,?,?,?)";
    public static final String ADD_ELECTRIC_CAR_SQL = "insert into ElectricCar values (?,?,?,?,?,?,?,?,?,?,?)";
    public static final String ADD_MOTOR_CYCLE_SQL = "insert into Motorcycle values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String GET_ALL_ELECTRIC_CARS = "select VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, BatteryCapacity, ChargeLevel from ElectricCar";
    public static final String GET_ALL_FOSSIL_CARS = "select VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, FuelType, FuelAmount from FossilCar";
    public static final String GET_ALL_MOTOR_CYCLES = "select VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, HasSidecar, EngineCapacity, IsModified, NumberOfWheels from Motorcycle";
    public static final String GET_TOTAL_FUEL_AMOUNT = "select SUM(FuelAmount) AS TotalFuel from FossilCar";
    public static final String GET_ALL_DRIVEABLE_FOSSIL_CARS = "select VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, FuelType, FuelAmount from FossilCar where Driveable = true";
    public static final String GET_ALL_DRIVEABLE_ELECTRIC_CARS = "select VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, BatteryCapacity, ChargeLevel from ElectricCar where Driveable = true";
    public static final String GET_ALL_DRIVEABLE_MOTOR_CYCLES = "select VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, HasSidecar, EngineCapacity, IsModified, NumberOfWheels from Motorcycle where Driveable = true";
    public static final String GET_AMOUNT_OF_ELECTRIC_CARS = "select COUNT(*) AS TotalElectricCars from ElectricCar";
    public static final String GET_AMOUNT_OF_FOSSIL_CARS = "select COUNT(*) AS TotalFossilCars from FossilCar";
    public static final String GET_AMOUNT_OF_MOTOR_CYCLES = "select COUNT(*) AS TotalMotorCycles from Motorcycle";

    public ScrapYardService() {
        scrapYardDS = new MysqlDataSource();
        scrapYardDS.setPassword(PropertiesProvider.PROPS.getProperty("pwd"));
        scrapYardDS.setUser(PropertiesProvider.PROPS.getProperty("uname"));
        scrapYardDS.setDatabaseName(PropertiesProvider.PROPS.getProperty("db_name"));
        scrapYardDS.setPortNumber(Integer.parseInt(PropertiesProvider.PROPS.getProperty("port")));
        scrapYardDS.setServerName(PropertiesProvider.PROPS.getProperty("host"));
    }

    public void insertScrapeYard(ScrapYard scrapYard) throws SQLException {
        try (Connection connection = scrapYardDS.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_SCRAPE_YARDS_SQL);
        ) {
            statement.setInt(1, scrapYard.getScrapyardID());
            statement.setString(2, scrapYard.getName());
            statement.setString(3, scrapYard.getAddress());
            statement.setString(4, scrapYard.getPhoneNumber());
            statement.executeUpdate();
        }
    }

    public void insertFossilCar(FossilCar fossilCar) throws SQLException {
        try (Connection connection = scrapYardDS.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_FOSSIL_CAR_SQL);
        ) {
            statement.setInt(1, fossilCar.getVehicleId());
            statement.setString(2, fossilCar.getBrand());
            statement.setString(3, fossilCar.getModel());
            statement.setInt(4, fossilCar.getYearModel());
            statement.setString(5, fossilCar.getRegistrationNumber());
            statement.setString(6, fossilCar.getChassisNumber());
            statement.setBoolean(7, fossilCar.isDriveable());
            statement.setInt(8, fossilCar.getNumberOfSellableWheels());
            statement.setInt(9, fossilCar.getScrapYardId());
            statement.setString(10, fossilCar.getFuelType());
            statement.setInt(11, fossilCar.getFuelAmount());
            statement.executeUpdate();
        }
    }

    public void insertElectricCar(ElectricCar electricCar) throws SQLException {
        try (Connection connection = scrapYardDS.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ELECTRIC_CAR_SQL);
        ) {
            statement.setInt(1, electricCar.getVehicleId());
            statement.setString(2, electricCar.getBrand());
            statement.setString(3, electricCar.getModel());
            statement.setInt(4, electricCar.getYearModel());
            statement.setString(5, electricCar.getRegistrationNumber());
            statement.setString(6, electricCar.getChassisNumber());
            statement.setBoolean(7, electricCar.isDriveable());
            statement.setInt(8, electricCar.getNumberOfSellableWheels());
            statement.setInt(9, electricCar.getScrapYardId());
            statement.setInt(10, electricCar.getBatteryCapacity());
            statement.setInt(11, electricCar.getChargeLevel());
            statement.executeUpdate();
        }
    }

    public void insertMotorcycle(Motorcycle motorcycle) throws SQLException {
        try (Connection connection = scrapYardDS.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_MOTOR_CYCLE_SQL);
        ) {
            statement.setInt(1, motorcycle.getVehicleId());
            statement.setString(2, motorcycle.getBrand());
            statement.setString(3, motorcycle.getModel());
            statement.setInt(4, motorcycle.getYearModel());
            statement.setString(5, motorcycle.getRegistrationNumber());
            statement.setString(6, motorcycle.getChassisNumber());
            statement.setBoolean(7, motorcycle.isDriveable());
            statement.setInt(8, motorcycle.getNumberOfSellableWheels());
            statement.setInt(9, motorcycle.getScrapYardId());
            statement.setBoolean(10, motorcycle.isHasSidecar());
            statement.setInt(11, motorcycle.getEngineCapacity());
            statement.setBoolean(12, motorcycle.isModified());
            statement.setInt(13, motorcycle.getNumberOfWheels());
            statement.executeUpdate();
        }
    }

    public void insertAllVehicles(List<Vehicle> vehicles) throws SQLException {
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof FossilCar) {
                insertFossilCar((FossilCar) vehicle);
            } else if (vehicle instanceof ElectricCar) {
                insertElectricCar((ElectricCar) vehicle);
            } else {
                insertMotorcycle((Motorcycle) vehicle);
            }
        }
    }

    private List<ElectricCar> getAllElectricCars() throws SQLException {
        List<ElectricCar> electricCars = new ArrayList<>();
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_ELECTRIC_CARS);
        ) {
            while (rs.next()) {
                int vehicleID = rs.getInt("VehicleID");
                int scrapyardID = rs.getInt("ScrapyardID");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                int yearModel = rs.getInt("YearModel");
                String registrationNumber = rs.getString("RegistrationNumber");
                String chassisNumber = rs.getString("ChassisNumber");
                boolean driveable = rs.getBoolean("Driveable");
                int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");
                int batteryCapacity = rs.getInt("BatteryCapacity");
                int chargeLevel = rs.getInt("ChargeLevel");
                ElectricCar electricCar = new ElectricCar(vehicleID, scrapyardID, "ElectricCar", brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, batteryCapacity, chargeLevel);
                electricCars.add(electricCar);
            }
        }
        return electricCars;
    }

    private List<FossilCar> getAllFossilCars() throws SQLException {
        List<FossilCar> fossilCars = new ArrayList<>();
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_FOSSIL_CARS);
        ) {
            while (rs.next()) {
                int vehicleID = rs.getInt("VehicleID");
                int scrapyardID = rs.getInt("ScrapyardID");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                int yearModel = rs.getInt("YearModel");
                String registrationNumber = rs.getString("RegistrationNumber");
                String chassisNumber = rs.getString("ChassisNumber");
                boolean driveable = rs.getBoolean("Driveable");
                int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");
                String fuelType = rs.getString("FuelType");
                int fuelAmount = rs.getInt("FuelAmount");
                FossilCar fossilCar = new FossilCar(vehicleID, scrapyardID, "FossilCar", brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, fuelType, fuelAmount);
                fossilCars.add(fossilCar);
            }
        }
        return fossilCars;
    }

    private List<Motorcycle> getAllMotorCycles() throws SQLException {
        List<Motorcycle> motorcycles = new ArrayList<>();
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_MOTOR_CYCLES);
        ) {
            while (rs.next()) {
                int vehicleID = rs.getInt("VehicleID");
                int scrapyardID = rs.getInt("ScrapyardID");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                int yearModel = rs.getInt("YearModel");
                String registrationNumber = rs.getString("RegistrationNumber");
                String chassisNumber = rs.getString("ChassisNumber");
                boolean driveable = rs.getBoolean("Driveable");
                int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");
                boolean hasSidecar = rs.getBoolean("HasSidecar");
                int engineCapacity = rs.getInt("EngineCapacity");
                boolean isModified = rs.getBoolean("IsModified");
                int numberOfWheels = rs.getInt("NumberOfWheels");
                Motorcycle motorcycle = new Motorcycle(vehicleID, scrapyardID, "Motorcycle", brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, hasSidecar, engineCapacity, isModified, numberOfWheels);
                motorcycles.add(motorcycle);
            }
        }
        return motorcycles;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.addAll(getAllElectricCars());
        vehicles.addAll(getAllFossilCars());
        vehicles.addAll(getAllMotorCycles());
        return vehicles;
    }

    public void getTotalAmountOfFuel() throws SQLException {
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_TOTAL_FUEL_AMOUNT);
        ) {
            while (rs.next()) {
                int totalFuel = rs.getInt("TotalFuel");
                System.out.println("Total fuel: " + totalFuel);
            }
        }
    }

    private List<ElectricCar> getAllDriveableElectricCars() throws SQLException {
        List<ElectricCar> electricCars = new ArrayList<>();
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_DRIVEABLE_ELECTRIC_CARS);
        ) {
            while (rs.next()) {
                int vehicleID = rs.getInt("VehicleID");
                int scrapyardID = rs.getInt("ScrapyardID");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                int yearModel = rs.getInt("YearModel");
                String registrationNumber = rs.getString("RegistrationNumber");
                String chassisNumber = rs.getString("ChassisNumber");
                boolean driveable = rs.getBoolean("Driveable");
                int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");
                int batteryCapacity = rs.getInt("BatteryCapacity");
                int chargeLevel = rs.getInt("ChargeLevel");
                ElectricCar electricCar = new ElectricCar(vehicleID, scrapyardID, "ElectricCar", brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, batteryCapacity, chargeLevel);
                electricCars.add(electricCar);
            }
        }
        return electricCars;
    }

    private List<FossilCar> getAllDriveableFossilCars() throws SQLException {
        List<FossilCar> fossilCars = new ArrayList<>();
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_DRIVEABLE_FOSSIL_CARS);
        ) {
            while (rs.next()) {
                int vehicleID = rs.getInt("VehicleID");
                int scrapyardID = rs.getInt("ScrapyardID");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                int yearModel = rs.getInt("YearModel");
                String registrationNumber = rs.getString("RegistrationNumber");
                String chassisNumber = rs.getString("ChassisNumber");
                boolean driveable = rs.getBoolean("Driveable");
                int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");
                String fuelType = rs.getString("FuelType");
                int fuelAmount = rs.getInt("FuelAmount");
                FossilCar fossilCar = new FossilCar(vehicleID, scrapyardID, "FossilCar", brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, fuelType, fuelAmount);
                fossilCars.add(fossilCar);
            }
        }
        return fossilCars;
    }

    private List<Motorcycle> getAllDriveableMotorCycles() throws SQLException {
        List<Motorcycle> motorcycles = new ArrayList<>();
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_DRIVEABLE_MOTOR_CYCLES);
        ) {
            while (rs.next()) {
                int vehicleID = rs.getInt("VehicleID");
                int scrapyardID = rs.getInt("ScrapyardID");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                int yearModel = rs.getInt("YearModel");
                String registrationNumber = rs.getString("RegistrationNumber");
                String chassisNumber = rs.getString("ChassisNumber");
                boolean driveable = rs.getBoolean("Driveable");
                int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");
                boolean hasSidecar = rs.getBoolean("HasSidecar");
                int engineCapacity = rs.getInt("EngineCapacity");
                boolean isModified = rs.getBoolean("IsModified");
                int numberOfWheels = rs.getInt("NumberOfWheels");
                Motorcycle motorcycle = new Motorcycle(vehicleID, scrapyardID, "Motorcycle", brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, hasSidecar, engineCapacity, isModified, numberOfWheels);
                motorcycles.add(motorcycle);
            }
        }
        return motorcycles;
    }

    public List<Vehicle> getAllDriveableVehicles() throws SQLException {
        List<Vehicle> allDriveableVehicles = new ArrayList<>();
        allDriveableVehicles.addAll(getAllDriveableElectricCars());
        allDriveableVehicles.addAll(getAllDriveableFossilCars());
        allDriveableVehicles.addAll(getAllDriveableMotorCycles());
        return allDriveableVehicles;
    }

    private int getTotalAmountOfElectricCars() throws SQLException {
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_AMOUNT_OF_ELECTRIC_CARS);
        ) {
            rs.next();
            return rs.getInt("TotalElectricCars");
        }
    }

    private int getTotalAmountOfFossilCars() throws SQLException {
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_AMOUNT_OF_FOSSIL_CARS);
        ) {
            rs.next();
            return rs.getInt("TotalFossilCars");
        }
    }

    private int getTotalAmountOfMotorCycles() throws SQLException {
        try (Connection connection = scrapYardDS.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_AMOUNT_OF_MOTOR_CYCLES);
        ) {
            rs.next();
            return rs.getInt("TotalMotorCycles");
        }
    }

    public void getVehicleAmountByType() throws SQLException {
        int electricCars = getTotalAmountOfElectricCars();
        int fossilCars = getTotalAmountOfFossilCars();
        int motorCycles = getTotalAmountOfMotorCycles();
        int total = getTotalAmountOfElectricCars() + getTotalAmountOfFossilCars() + getTotalAmountOfMotorCycles();
        System.out.println("Electric cars: " + electricCars);
        System.out.println("Fossil cars: " + fossilCars);
        System.out.println("Motorcycles: " + motorCycles);
        System.out.println("Total amount of vehicles: " + total);
    }
}


