package pl.nankiewic.fleetappbackend.chart.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.chart.ChartType;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InsuranceChartDataProvider extends AbstractChartDataProvider {

    private final VehicleInsuranceRepository vehicleInsuranceRepository;

    public InsuranceChartDataProvider(final VehicleInsuranceRepository vehicleInsuranceRepository) {
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
    }

    @Override
    public ChartDataRespondDTO getSummaryCostByVehicleOwner(Long userId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(vehicleInsuranceRepository.findSummaryCostByVehicleOwner(userId, startDate, endDate))
                .map(v -> buildChartDataRespondDTO(v, getSupportedType()))
                .orElseThrow();
    }

    @Override
    public ChartDataRespondDTO getSummaryCostByVehicleId(Long vehicleId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(vehicleInsuranceRepository.findSummaryCostByVehicleId(vehicleId, startDate, endDate))
                .map(v -> buildChartDataRespondDTO(v, getSupportedType()))
                .orElseThrow();
    }

    @Override
    public ChartType getSupportedType() {
        return ChartType.INSURANCE_COST;
    }

}
