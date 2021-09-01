package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataRefuelingDTO {
    private BigDecimal value;
    private LocalDate date;

}
