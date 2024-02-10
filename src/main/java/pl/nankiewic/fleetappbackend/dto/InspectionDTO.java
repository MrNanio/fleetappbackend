package pl.nankiewic.fleetappbackend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectionDTO {

    private Long id;
    private Long vehicleId;
    private LocalDate inspectionDate;
    private LocalDate expirationDate;
    private BigDecimal cost;
    private String description;

}
