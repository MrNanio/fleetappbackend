package pl.nankiewic.fleetappbackend.Entity;

import pl.nankiewic.fleetappbackend.Entity.Enum.EnumVehicleStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicle_status")
public class VehicleStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_status", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumVehicleStatus vehicleStatus;

    @OneToMany(mappedBy = "vehicleStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles = new HashSet<>();

    public VehicleStatus(Long id, EnumVehicleStatus vehicleStatus) {
        this.id = id;
        this.vehicleStatus = vehicleStatus;
    }

    public VehicleStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumVehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(EnumVehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
