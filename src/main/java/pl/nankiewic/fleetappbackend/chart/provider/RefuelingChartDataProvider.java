package pl.nankiewic.fleetappbackend.chart.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.chart.ChartType;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.repository.VehicleRefuelingRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RefuelingChartDataProvider extends AbstractChartDataProvider {

    private final VehicleRefuelingRepository vehicleRefuelingRepository;

    public RefuelingChartDataProvider(final VehicleRefuelingRepository vehicleRefuelingRepository) {
        this.vehicleRefuelingRepository = vehicleRefuelingRepository;
    }

    @Override
    public ChartDataRespondDTO getSummaryCostByVehicleOwner(Long userId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(vehicleRefuelingRepository.findSummaryCostByVehicleOwner(userId, startDate, endDate))
                .map(v -> buildChartDataRespondDTO(v, getSupportedType()))
                .orElseThrow();
    }

    @Override
    public ChartDataRespondDTO getSummaryCostByVehicleId(Long vehicleId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(vehicleRefuelingRepository.findSummaryCostByVehicleId(vehicleId, startDate, endDate))
                .map(v -> buildChartDataRespondDTO(v, getSupportedType()))
                .orElseThrow();
    }

    @Override
    public ChartType getSupportedType() {
        return ChartType.REFUELING_COST;
    }

}
