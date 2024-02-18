package pl.nankiewic.fleetappbackend.dto.repair;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairDTO {

    private Long id;
    private Long vehicleId;
    private String title;
    private LocalDate repairDate;
    private BigDecimal cost;
    private String description;

}
