package pl.nankiewic.fleetappbackend.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumVehicleStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_status")
public class VehicleStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "vehicle_status", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumVehicleStatus vehicleStatus;

    @OneToMany(mappedBy = "vehicleStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles = new HashSet<>();

}
