package pl.nankiewic.fleetappbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
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
    @JsonIgnore
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

}
