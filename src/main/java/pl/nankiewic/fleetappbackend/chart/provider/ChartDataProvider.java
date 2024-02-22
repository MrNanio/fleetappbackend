package pl.nankiewic.fleetappbackend.chart.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.chart.ChartType;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChartDataProvider {

    private final List<AbstractChartDataProvider> chartDataProviders;

    public ChartDataProvider(final List<AbstractChartDataProvider> chartServices) {
        this.chartDataProviders = chartServices;
    }

    public ChartDataRespondDTO getSummaryCostByVehicleOwner(ChartType type, Long userId, LocalDate startDate, LocalDate endDate) {
        return getChartDataProvider(type).getSummaryCostByVehicleOwner(userId, startDate, endDate);
    }

    public ChartDataRespondDTO getSummaryCostByVehicleId(ChartType type, Long vehicleId, LocalDate startDate, LocalDate endDate) {
        return getChartDataProvider(type).getSummaryCostByVehicleId(vehicleId, startDate, endDate);
    }

    private AbstractChartDataProvider getChartDataProvider(ChartType type) {
        return chartDataProviders.stream()
                .filter(chartService -> chartService.isSupported(type))
                .findFirst()
                .orElseThrow();
    }

}