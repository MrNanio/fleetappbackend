package pl.nankiewic.fleetappbackend.report.view.vehicle;

import pl.nankiewic.fleetappbackend.report.view.ReportView;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VehicleRepairReportView extends ReportView {

    Long getVehicleId();

    String getDescription();

    String getTitle();

    BigDecimal getCost();

    LocalDate repairDate();

}
