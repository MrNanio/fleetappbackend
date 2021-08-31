package pl.nankiewic.fleetappbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumFuelType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fuel_types")
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fuel_type")
    private EnumFuelType fuelType;

    @JsonIgnore
    @OneToMany(mappedBy = "fuelType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicle = new HashSet<>();

    public FuelType(Long id, EnumFuelType fuelType) {
        this.id = id;
        this.fuelType = fuelType;
    }
}
