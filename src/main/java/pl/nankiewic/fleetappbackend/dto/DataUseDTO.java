package pl.nankiewic.fleetappbackend.dto;

import lombok.*;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataUseDTO {
    private Vehicle vehicle;
    private Long cost;

}
