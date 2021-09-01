package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareDTO {

    private Long userId;
    private List<String> vehicleId;

}
