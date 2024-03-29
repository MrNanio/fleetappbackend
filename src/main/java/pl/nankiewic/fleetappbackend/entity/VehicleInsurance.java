package pl.nankiewic.fleetappbackend.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_insurances")
public class VehicleInsurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "effective_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDate;

    @Column(name = "expiration_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @Column(name = "cost", precision = 8, scale = 2, nullable = false)
    private BigDecimal cost;

    @Column(name = "policy_number", length = 25, nullable = false)
    private String policyNumber;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "insurance_type", length = 45, nullable = false)
    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

}
