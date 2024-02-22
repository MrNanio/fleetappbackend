package pl.nankiewic.fleetappbackend.report.view.vehicle;

import pl.nankiewic.fleetappbackend.report.view.ReportView;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VehicleRefuelingReportView extends ReportView {

    Long getVehicleId();

    String getDescription();

    String getLitre();

    BigDecimal cost();

    LocalDate refuelingDate();

}
