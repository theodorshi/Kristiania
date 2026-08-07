public abstract class Vehicle {
    private int vehicleId;
    private int scrapYardId;
    private String vehicleType;
    private String brand;
    private String model;
    private int yearModel;
    private String registrationNumber;
    private String chassisNumber;
    private boolean driveable;
    private int numberOfSellableWheels;

    public Vehicle(int vehicleId, int scrapYardId, String vehicleType, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels) {
        this.vehicleId = vehicleId;
        this.scrapYardId = scrapYardId;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.model = model;
        this.yearModel = yearModel;
        this.registrationNumber = registrationNumber;
        this.chassisNumber = chassisNumber;
        this.driveable = driveable;
        this.numberOfSellableWheels = numberOfSellableWheels;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getScrapYardId() {
        return scrapYardId;
    }

    public void setScrapYardId(int scrapYardId) {
        this.scrapYardId = scrapYardId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearModel() {
        return yearModel;
    }

    public void setYearModel(int yearModel) {
        this.yearModel = yearModel;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public boolean isDriveable() {
        return driveable;
    }

    public void setDriveable(boolean driveable) {
        this.driveable = driveable;
    }

    public int getNumberOfSellableWheels() {
        return numberOfSellableWheels;
    }

    public void setNumberOfSellableWheels(int numberOfSellableWheels) {
        this.numberOfSellableWheels = numberOfSellableWheels;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", scrapYardId=" + scrapYardId +
                ", vehicleType='" + vehicleType + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", yearModel=" + yearModel +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", driveable=" + driveable +
                ", numberOfSellableWheels=" + numberOfSellableWheels +
                '}';
    }
}
