package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartDataRespondDTO {

    private Float value;
    private String name;

}
