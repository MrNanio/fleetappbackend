package pl.nankiewic.fleetappbackend.DTO;

public class VehicleDTO {

    private Long id;
    private String make;
    private String model;
    private String year;
    private String color;
    private String mileage;
    private String vinNumber;
    private String vehicleRegistrationNumber;
    private String fuelType;
    private String cityFuelConsumption;
    private String countryFuelConsumption;
    private String averageFuelConsumption;
    private String vehicleStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCityFuelConsumption() {
        return cityFuelConsumption;
    }

    public void setCityFuelConsumption(String cityFuelConsumption) {
        this.cityFuelConsumption = cityFuelConsumption;
    }

    public String getCountryFuelConsumption() {
        return countryFuelConsumption;
    }

    public void setCountryFuelConsumption(String countryFuelConsumption) {
        this.countryFuelConsumption = countryFuelConsumption;
    }

    public String getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(String averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
