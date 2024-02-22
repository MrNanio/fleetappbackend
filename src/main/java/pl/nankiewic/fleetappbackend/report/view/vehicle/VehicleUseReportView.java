package pl.nankiewic.fleetappbackend.report.view.vehicle;

import pl.nankiewic.fleetappbackend.entity.enums.TripType;
import pl.nankiewic.fleetappbackend.report.view.ReportView;

import java.time.LocalDate;

public interface VehicleUseReportView extends ReportView {

    Long getVehicleId();

    Short getTrip();

    LocalDate getTripDate();

    String getDescription();

    TripType getTripType();

}
