package pl.nankiewic.fleetappbackend.Entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_make_id")
    private VehicleMake vehicleMake;

    @Column(name = "model", length = 45, nullable = false)
    private String model;

    @Column(name = "year", length = 4, nullable = false)
    private String year;

    @Column(name = "color", length = 45, nullable = false)
    private String color;

    @Column(name = "mileage", length = 7, nullable = false)
    private String mileage;

    @Column(name = "vin_number", length = 17, nullable = false)
    private String vinNumber;

    @Column(name = "vehicle_registration_number", length = 10, nullable = false)
    private String vehicleRegistrationNumber;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id", nullable = false)
    private FuelType fuelType;

    @Column(name = "city_fuel_consumption", precision = 4, scale = 2, nullable = false)
    private BigDecimal cityFuelConsumption;

    @Column(name = "country_fuel_consumption", precision = 4, scale = 2, nullable = false)
    private BigDecimal countryFuelConsumption;

    @Column(precision = 4, scale = 2, nullable = false)
    private BigDecimal averageFuelConsumption;

    @ManyToOne
    @JoinColumn(name = "vehicle_status_id", nullable = false)
    private VehicleStatus vehicleStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleInspection> vehicleInspections = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleRefueling> refuelings = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleInsurance> vehicleInsurances = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleRepair> vehicleRepairs = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleUse> vehicleUses = new HashSet<>();

    @OneToOne(mappedBy = "vehicle")
    private CurrentVehicleUser currentVehicleUser;

    public Vehicle() {
    }

    public Vehicle(Long id, VehicleMake vehicleMake, String model, String year, String color,
                   String mileage, String vinNumber, String vehicleRegistrationNumber, FuelType fuelType,
                   BigDecimal cityFuelConsumption, BigDecimal countryFuelConsumption, BigDecimal averageFuelConsumption,
                   VehicleStatus vehicleStatus, User user) {
        this.id = id;
        this.vehicleMake = vehicleMake;
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
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleMake getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(VehicleMake vehicleMake) {
        this.vehicleMake = vehicleMake;
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

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
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

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<VehicleInspection> getVehicleInspections() {
        return vehicleInspections;
    }

    public void setVehicleInspections(Set<VehicleInspection> vehicleInspections) {
        this.vehicleInspections = vehicleInspections;
    }

    public Set<VehicleRefueling> getRefuelings() {
        return refuelings;
    }

    public void setRefuelings(Set<VehicleRefueling> refuelings) {
        this.refuelings = refuelings;
    }

    public Set<VehicleInsurance> getVehicleInsurances() {
        return vehicleInsurances;
    }

    public void setVehicleInsurances(Set<VehicleInsurance> vehicleInsurances) {
        this.vehicleInsurances = vehicleInsurances;
    }

    public Set<VehicleRepair> getVehicleRepairs() {
        return vehicleRepairs;
    }

    public void setVehicleRepairs(Set<VehicleRepair> vehicleRepairs) {
        this.vehicleRepairs = vehicleRepairs;
    }

    public Set<VehicleUse> getVehicleUses() {
        return vehicleUses;
    }

    public void setVehicleUses(Set<VehicleUse> vehicleUses) {
        this.vehicleUses = vehicleUses;
    }

    public CurrentVehicleUser getCurrentVehicleUser() {
        return currentVehicleUser;
    }

    public void setCurrentVehicleUser(CurrentVehicleUser currentVehicleUser) {
        this.currentVehicleUser = currentVehicleUser;
    }
}
