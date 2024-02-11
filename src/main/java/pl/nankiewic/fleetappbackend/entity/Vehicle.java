package pl.nankiewic.fleetappbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleMake;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "make", length = 45, nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleMake make;

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

    @Column(name = "fuel_type", length = 5, nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name = "city_fuel_consumption", precision = 4, scale = 2, nullable = false)
    private BigDecimal cityFuelConsumption;

    @Column(name = "country_fuel_consumption", precision = 4, scale = 2, nullable = false)
    private BigDecimal countryFuelConsumption;

    @Column(precision = 4, scale = 2, nullable = false)
    private BigDecimal averageFuelConsumption;

    @Column(name = "vehicle_status", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
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

}
