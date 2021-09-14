package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumInsuranceType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceRequestDTO {

    private Long id;
    private Long vehicleId;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;
    private BigDecimal cost;
    private String policyNumber;
    private String insuranceType;
    private String description;
}