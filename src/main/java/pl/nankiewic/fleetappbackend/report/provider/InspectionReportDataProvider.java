package pl.nankiewic.fleetappbackend.report.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleInspectionReportView;
import pl.nankiewic.fleetappbackend.repository.VehicleInspectionRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import java.util.List;

@Service
public class InspectionReportDataProvider extends AbstractVehicleReportDataProvider<VehicleInspectionReportView> {

    private final VehicleInspectionRepository vehicleInspectionRepository;

    public InspectionReportDataProvider(final VehicleRepository vehicleRepository,
                                        final VehicleInspectionRepository vehicleInspectionRepository) {
        super(vehicleRepository);
        this.vehicleInspectionRepository = vehicleInspectionRepository;
    }

    @Override
    public List<VehicleInspectionReportView> getReportData(ReportViewFilterParam param) {
        return vehicleInspectionRepository.findVehicleInspectionReportViewByParam(param);
    }

}
