package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataUseTypeDTO {
    private String type;
    private Long cost;

}
