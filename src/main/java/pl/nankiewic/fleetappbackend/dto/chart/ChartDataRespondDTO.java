package pl.nankiewic.fleetappbackend.dto.chart;

import lombok.*;
import pl.nankiewic.fleetappbackend.chart.ChartType;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartDataRespondDTO {

    private BigDecimal value;
    private ChartType name;

}
