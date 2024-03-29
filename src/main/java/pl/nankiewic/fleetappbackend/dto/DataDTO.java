package pl.nankiewic.fleetappbackend.dto;

import lombok.*;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDTO {
    private Vehicle vehicle;
    private BigDecimal cost;

}
