package pl.nankiewic.fleetappbackend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefuelingDTO {
    private Long id;
    private Long vehicleId;
    private Long userId;
    private LocalDate refuelingDate;
    private String litre;
    private BigDecimal cost;
    private String description;

}
