package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataTripUserDTO {
    private Vehicle vehicle;
    private Long trip;

}
