package pl.nankiewic.fleetappbackend.report.view.vehicle;

import pl.nankiewic.fleetappbackend.report.view.ReportView;

import java.time.LocalDate;

public interface VehicleInspectionReportView extends ReportView {

    Long getVehicleId();

    LocalDate getInspectionDate();

    LocalDate getExpirationDate();

    LocalDate getCost();

    String getDescription();

}
