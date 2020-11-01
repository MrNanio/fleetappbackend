package pl.nankiewic.fleetappbackend.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "VehcleMakeFk")
    private VehicleMake vehicleMake;

    @Column(length = 45, nullable = false)
    private String model;

    @Column(length = 4, nullable = false)
    private String year;

    @Column(length = 45, nullable = false)
    private String color;

    @Column(length = 7, nullable = false)
    private String mileage;

    @Column(length = 17, nullable = false)
    private String vinNumber;

    @Column(length = 10, nullable = false)
    private String vehicleRegistrationNumber;

    @ManyToOne
    @JoinColumn(name = "FuelTypeFK", nullable = false)
    private FuelType fuelType;

    @Column(precision=4, scale=2, nullable = false)
    private BigDecimal cityFuelConsumption;

    @Column(precision=4, scale=2, nullable = false)
    private BigDecimal countryFuelConsumption;

    @Column(precision=4, scale=2, nullable = false)
    private BigDecimal averageFuelConsumption;


    @ManyToOne
    @JoinColumn(name = "VehicleStatusFk", nullable = false)
    private VehicleStatus vehicleStatus;

    @ManyToOne
    @JoinColumn(name = "UserFk", nullable = false)
    private User user;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleInspection> vehicleInspections = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Refueling> refuelings = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleInsurance> vehicleInsurances = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleRepair> vehicleRepairs = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleUse> vehicleUses = new HashSet<>();

    public Vehicle() {
    }
    @JsonIgnore
    public Long getId() {
        return id;
    }
    @JsonProperty
    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public VehicleMake getVehicleMake() {
        return vehicleMake;
    }
    @JsonProperty
    public void setVehicleMake(VehicleMake vehicleMake) {
        this.vehicleMake = vehicleMake;
    }
    @JsonIgnore
    public String getModel() {
        return model;
    }
    @JsonProperty
    public void setModel(String model) {
        this.model = model;
    }
    @JsonIgnore
    public String getYear() {
        return year;
    }
    @JsonProperty
    public void setYear(String year) {
        this.year = year;
    }
    @JsonIgnore
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @JsonIgnore
    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
    @JsonIgnore
    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }
    @JsonIgnore
    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }
    @JsonIgnore
    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
    @JsonIgnore
    public BigDecimal getCityFuelConsumption() {
        return cityFuelConsumption;
    }

    public void setCityFuelConsumption(BigDecimal cityFuelConsumption) {
        this.cityFuelConsumption = cityFuelConsumption;
    }
    @JsonIgnore
    public BigDecimal getCountryFuelConsumption() {
        return countryFuelConsumption;
    }

    public void setCountryFuelConsumption(BigDecimal countryFuelConsumption) {
        this.countryFuelConsumption = countryFuelConsumption;
    }
    @JsonIgnore
    public BigDecimal getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(BigDecimal averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }
    @JsonIgnore
    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @JsonIgnore
    public Set<VehicleInspection> getVehicleInspections() {
        return vehicleInspections;
    }

    public void setVehicleInspections(Set<VehicleInspection> vehicleInspections) {
        this.vehicleInspections = vehicleInspections;
    }
    @JsonIgnore
    public Set<Refueling> getRefuelings() {
        return refuelings;
    }

    public void setRefuelings(Set<Refueling> refuelings) {
        this.refuelings = refuelings;
    }
    @JsonIgnore
    public Set<VehicleInsurance> getVehicleInsurances() {
        return vehicleInsurances;
    }

    public void setVehicleInsurances(Set<VehicleInsurance> vehicleInsurances) {
        this.vehicleInsurances = vehicleInsurances;
    }
    @JsonIgnore
    public Set<VehicleRepair> getVehicleRepairs() {
        return vehicleRepairs;
    }

    public void setVehicleRepairs(Set<VehicleRepair> vehicleRepairs) {
        this.vehicleRepairs = vehicleRepairs;
    }
    @JsonIgnore
    public Set<VehicleUse> getVehicleUses() {
        return vehicleUses;
    }

    public void setVehicleUses(Set<VehicleUse> vehicleUses) {
        this.vehicleUses = vehicleUses;
    }
}
