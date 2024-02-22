package pl.nankiewic.fleetappbackend.chart.provider;

import pl.nankiewic.fleetappbackend.chart.ChartType;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class AbstractChartDataProvider {

    public abstract ChartDataRespondDTO getSummaryCostByVehicleOwner(Long userId, LocalDate startDate, LocalDate endDate);

    public abstract ChartDataRespondDTO getSummaryCostByVehicleId(Long vehicleId, LocalDate startDate, LocalDate endDate);

    public abstract ChartType getSupportedType();

    public boolean isSupported(ChartType type) {
        return type == getSupportedType();
    }

    ChartDataRespondDTO buildChartDataRespondDTO(BigDecimal value, ChartType type) {
        return ChartDataRespondDTO.builder()
                .value(value)
                .name(type)
                .build();
    }

}