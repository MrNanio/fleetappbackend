package pl.nankiewic.fleetappbackend.chart.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.chart.ChartType;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.repository.VehicleRepairRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RepairChartDataProvider extends AbstractChartDataProvider {

    private final VehicleRepairRepository vehicleRepairRepository;

    public RepairChartDataProvider(final VehicleRepairRepository vehicleRepairRepository) {
        this.vehicleRepairRepository = vehicleRepairRepository;
    }

    @Override
    public ChartDataRespondDTO getSummaryCostByVehicleOwner(Long userId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(vehicleRepairRepository.findSummaryCostByVehicleOwner(userId, startDate, endDate))
                .map(v -> buildChartDataRespondDTO(v, getSupportedType()))
                .orElseThrow();
    }

    @Override
    public ChartDataRespondDTO getSummaryCostByVehicleId(Long vehicleId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(vehicleRepairRepository.findSummaryCostByVehicleId(vehicleId, startDate, endDate))
                .map(v -> buildChartDataRespondDTO(v, getSupportedType()))
                .orElseThrow();
    }

    @Override
    public ChartType getSupportedType() {
        return ChartType.REPAIR_COST;
    }

}
