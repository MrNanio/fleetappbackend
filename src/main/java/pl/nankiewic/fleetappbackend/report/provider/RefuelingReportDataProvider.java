package pl.nankiewic.fleetappbackend.report.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleRefuelingReportView;
import pl.nankiewic.fleetappbackend.repository.VehicleRefuelingRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import java.util.List;

@Service
public class RefuelingReportDataProvider extends AbstractVehicleReportDataProvider<VehicleRefuelingReportView> {

    private final VehicleRefuelingRepository vehicleRefuelingRepository;

    public RefuelingReportDataProvider(final VehicleRepository vehicleRepository,
                                       final VehicleRefuelingRepository vehicleRefuelingRepository) {
        super(vehicleRepository);
        this.vehicleRefuelingRepository = vehicleRefuelingRepository;
    }

    @Override
    public List<VehicleRefuelingReportView> getReportData(ReportViewFilterParam param) {
        return vehicleRefuelingRepository.findVehicleRefuelingReportViewsByParam(param);
    }

}