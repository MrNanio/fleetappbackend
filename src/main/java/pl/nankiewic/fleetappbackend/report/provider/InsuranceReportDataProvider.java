package pl.nankiewic.fleetappbackend.report.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleInsuranceReportView;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import java.util.List;

@Service
public class InsuranceReportDataProvider extends AbstractVehicleReportDataProvider<VehicleInsuranceReportView> {

    private final VehicleInsuranceRepository vehicleInsuranceRepository;

    public InsuranceReportDataProvider(final VehicleRepository vehicleRepository,
                                       final VehicleInsuranceRepository vehicleInsuranceRepository) {
        super(vehicleRepository);
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
    }

    @Override
    public List<VehicleInsuranceReportView> getReportData(ReportViewFilterParam param) {
        return vehicleInsuranceRepository.findVehicleInsuranceReportViewsByParam(param);
    }

}