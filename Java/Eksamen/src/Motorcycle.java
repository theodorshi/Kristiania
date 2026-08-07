public class Motorcycle extends Vehicle{
    private boolean hasSidecar;
    private int engineCapacity;
    private boolean isModified;
    private int numberOfWheels;

    public Motorcycle(int vehicleId, int scrapYardId, String vehicleType, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, boolean hasSidecar, int engineCapacity, boolean isModified, int numberOfWheels) {
        super(vehicleId, scrapYardId, vehicleType, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels);
        this.hasSidecar = hasSidecar;
        this.engineCapacity = engineCapacity;
        this.isModified = isModified;
        this.numberOfWheels = numberOfWheels;
    }

    public boolean isHasSidecar() {
        return hasSidecar;
    }

    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "hasSidecar=" + hasSidecar +
                ", engineCapacity=" + engineCapacity +
                ", isModified=" + isModified +
                ", numberOfWheels=" + numberOfWheels +
                "} " + super.toString();
    }
}
