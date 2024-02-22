package pl.nankiewic.fleetappbackend.report.view.vehicle;

import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;
import pl.nankiewic.fleetappbackend.report.view.ReportView;

import java.time.LocalDate;

public interface VehicleInsuranceReportView extends ReportView {

    Long getVehicleId();

    LocalDate getEffectiveDate();

    LocalDate getExpirationDate();

    LocalDate getCost();

    String getDescription();

    InsuranceType getInsuranceType();

}