package pl.nankiewic.fleetappbackend.report.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleUseReportView;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleUseRepository;

import java.util.List;

@Service
public class UseReportDataProvider extends AbstractVehicleReportDataProvider<VehicleUseReportView> {

    private final VehicleUseRepository vehicleUseRepository;

    public UseReportDataProvider(final VehicleRepository vehicleRepository,
                                 final VehicleUseRepository vehicleUseRepository) {
        super(vehicleRepository);
        this.vehicleUseRepository = vehicleUseRepository;
    }

    @Override
    public List<VehicleUseReportView> getReportData(ReportViewFilterParam param) {
        return vehicleUseRepository.findVehicleUseReportViewsByParam(param);
    }

}