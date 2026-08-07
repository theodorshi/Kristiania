public class ElectricCar extends Vehicle{
    private int batteryCapacity;
    private int chargeLevel;

    public ElectricCar(int vehicleId, int scrapYardId, String vehicleType, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, int batteryCapacity, int chargeLevel) {
        super(vehicleId, scrapYardId, vehicleType, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels);
        this.batteryCapacity = batteryCapacity;
        this.chargeLevel = chargeLevel;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public int getChargeLevel() {
        return chargeLevel;
    }

    public void setChargeLevel(int chargeLevel) {
        this.chargeLevel = chargeLevel;
    }

    @Override
    public String toString() {
        return "ElectricCar{" +
                "batteryCapacity=" + batteryCapacity +
                ", chargeLevel=" + chargeLevel +
                "} " + super.toString();
    }
}
