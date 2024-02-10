package pl.nankiewic.fleetappbackend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareDTO {

    private Long userId;
    private List<Long> vehicleId;

}
