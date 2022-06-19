package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataFuelCostUserDTO {
    private BigDecimal cost;
    private Vehicle vehicle;


}
