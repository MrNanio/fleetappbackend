package pl.nankiewic.fleetappbackend.DTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class VehicleDTO {
    @NotNull
    private Long id;
    @NotNull
    private String make;
    @NotNull
    private String model;
    @NotNull
    private String year;
    @NotNull
    private String color;
    @NotNull
    private String mileage;
    @NotNull
    private String vinNumber;
    @NotNull
    private String vehicleRegistrationNumber;
    @NotNull
    private String fuelType;
    @NotNull
    private BigDecimal cityFuelConsumption;
    @NotNull
    private BigDecimal countryFuelConsumption;
    @NotNull
    private BigDecimal averageFuelConsumption;
    @NotNull
    private String vehicleStatus;

    public VehicleDTO(Long id, String make, String model, String year, String color, String mileage, String vinNumber,
                      String vehicleRegistrationNumber, String fuelType, BigDecimal cityFuelConsumption,
                      BigDecimal countryFuelConsumption, BigDecimal averageFuelConsumption, String vehicleStatus) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.mileage = mileage;
        this.vinNumber = vinNumber;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.fuelType = fuelType;
        this.cityFuelConsumption = cityFuelConsumption;
        this.countryFuelConsumption = countryFuelConsumption;
        this.averageFuelConsumption = averageFuelConsumption;
        this.vehicleStatus = vehicleStatus;
    }

    public VehicleDTO() {

    }

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

    public BigDecimal getCityFuelConsumption() {
        return cityFuelConsumption;
    }

    public void setCityFuelConsumption(BigDecimal cityFuelConsumption) {
        this.cityFuelConsumption = cityFuelConsumption;
    }

    public BigDecimal getCountryFuelConsumption() {
        return countryFuelConsumption;
    }

    public void setCountryFuelConsumption(BigDecimal countryFuelConsumption) {
        this.countryFuelConsumption = countryFuelConsumption;
    }

    public BigDecimal getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(BigDecimal averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
