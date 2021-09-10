package pl.nankiewic.fleetappbackend.Entity;

import lombok.*;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumInsuranceType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insurance_types")
public class InsuranceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "insurance_type", length = 45, nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumInsuranceType insuranceType;

    @OneToMany(mappedBy = "insuranceType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleInsurance> vehicleInsurances = new HashSet<>();

}
