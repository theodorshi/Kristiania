public class FossilCar extends Vehicle{
    private String fuelType;
    private int fuelAmount;

    public FossilCar(int vehicleId, int scrapYardId, String vehicleType, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, String fuelType, int fuelAmount) {
        super(vehicleId, scrapYardId, vehicleType, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels);
        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(int fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    @Override
    public String toString() {
        return "FossilCar{" +
                "fuelType='" + fuelType + '\'' +
                ", fuelAmount=" + fuelAmount +
                "} " + super.toString();
    }
}
