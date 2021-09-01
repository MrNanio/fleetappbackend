package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataUseDTO {
    private Vehicle vehicle;
    private Long cost;

}
