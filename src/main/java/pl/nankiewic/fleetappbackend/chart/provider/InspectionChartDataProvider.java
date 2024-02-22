package pl.nankiewic.fleetappbackend.chart.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.chart.ChartType;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.repository.VehicleInspectionRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InspectionChartDataProvider extends AbstractChartDataProvider {

    private final VehicleInspectionRepository vehicleInspectionRepository;

    public InspectionChartDataProvider(final VehicleInspectionRepository vehicleInspectionRepository) {
        this.vehicleInspectionRepository = vehicleInspectionRepository;
    }

    @Override
    public ChartDataRespondDTO getSummaryCostByVehicleOwner(Long userId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(vehicleInspectionRepository.findSummaryCostByVehicleOwner(userId, startDate, endDate))
                .map(v -> buildChartDataRespondDTO(v, getSupportedType()))
                .orElseThrow();
    }

    @Override
    public ChartDataRespondDTO getSummaryCostByVehicleId(Long vehicleId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(vehicleInspectionRepository.findSummaryCostByVehicleId(vehicleId, startDate, endDate))
                .map(v -> buildChartDataRespondDTO(v, getSupportedType()))
                .orElseThrow();
    }

    @Override
    public ChartType getSupportedType() {
        return ChartType.INSPECTION_COST;
    }

}