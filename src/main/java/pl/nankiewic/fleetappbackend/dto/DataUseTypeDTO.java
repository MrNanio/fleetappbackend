package pl.nankiewic.fleetappbackend.dto;

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
