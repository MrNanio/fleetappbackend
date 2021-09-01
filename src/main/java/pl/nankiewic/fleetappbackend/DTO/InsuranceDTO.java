package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

import java.math.BigDecimal;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceDTO {

    private Long id;
    private Long vehicleId;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;
    private BigDecimal cost;
    private String policyNumber;
    private String insuranceType;
    private String description;

}

