package pl.nankiewic.fleetappbackend.report.provider;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleRepairReportView;
import pl.nankiewic.fleetappbackend.repository.VehicleRepairRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import java.util.List;

@Service
public class RepairReportDataProvider extends AbstractVehicleReportDataProvider<VehicleRepairReportView> {

    private final VehicleRepairRepository vehicleRepairRepository;

    public RepairReportDataProvider(final VehicleRepository vehicleRepository,
                                    final VehicleRepairRepository vehicleRepairRepository) {
        super(vehicleRepository);
        this.vehicleRepairRepository = vehicleRepairRepository;
    }

    @Override
    public List<VehicleRepairReportView> getReportData(ReportViewFilterParam param) {
        return vehicleRepairRepository.findVehicleRepairReportViewsByParam(param);
    }

}