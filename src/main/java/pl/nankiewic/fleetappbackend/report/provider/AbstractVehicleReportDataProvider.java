package pl.nankiewic.fleetappbackend.report.provider;

import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.ReportView;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import java.util.List;

abstract class AbstractVehicleReportDataProvider<T extends ReportView> {

   private final VehicleRepository vehicleRepository;

   protected AbstractVehicleReportDataProvider(final VehicleRepository vehicleRepository) {
      this.vehicleRepository = vehicleRepository;
   }

   public abstract List<T> getReportData(ReportViewFilterParam param);

   public Vehicle getVehicleData(Long resourceId) {
      return vehicleRepository.getById(resourceId);
   }

}
