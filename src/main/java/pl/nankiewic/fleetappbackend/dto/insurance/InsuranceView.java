package pl.nankiewic.fleetappbackend.dto.insurance;

import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InsuranceView {

    Long getId();

    Long getVehicleId();

    LocalDate getEffectiveDate();

    LocalDate getExpirationDate();

    BigDecimal getCost();

    String getPolicyNumber();

    InsuranceType getIinsuranceType();

    String getDescription();
}
