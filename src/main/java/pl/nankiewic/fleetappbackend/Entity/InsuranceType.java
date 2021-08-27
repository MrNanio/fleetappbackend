package pl.nankiewic.fleetappbackend.Entity;

import pl.nankiewic.fleetappbackend.Entity.Enum.EnumInsuranceType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumInsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(EnumInsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Set<VehicleInsurance> getVehicleInsurances() {
        return vehicleInsurances;
    }

    public void setVehicleInsurances(Set<VehicleInsurance> vehicleInsurances) {
        this.vehicleInsurances = vehicleInsurances;
    }
}
