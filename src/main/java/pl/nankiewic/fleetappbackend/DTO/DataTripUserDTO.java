package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataTripUserDTO {
    private Vehicle vehicle;
    private Long trip;

}
