package pl.nankiewic.fleetappbackend.dto.insurance;

import lombok.*;

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
