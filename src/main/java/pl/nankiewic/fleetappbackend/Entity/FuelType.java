package pl.nankiewic.fleetappbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumFuelType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fuel_types")
public class FuelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fuel_type")
    private EnumFuelType fuelType;

    @JsonIgnore
    @OneToMany(mappedBy = "fuelType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicle = new HashSet<>();

    public FuelType() {
    }

    public FuelType(long l, EnumFuelType pb95) {
    }

    public FuelType(Long id, EnumFuelType fuelType) {
        this.id = id;
        this.fuelType = fuelType;
    }

    public Set<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(Set<Vehicle> vehicle) {
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumFuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(EnumFuelType fuelType) {
        this.fuelType = fuelType;
    }
}
