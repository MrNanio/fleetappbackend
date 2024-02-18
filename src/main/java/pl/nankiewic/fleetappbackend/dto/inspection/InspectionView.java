package pl.nankiewic.fleetappbackend.dto.inspection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InspectionView {

    Long getId();

    Long getVehicleId();

    LocalDate getInspectionDate();

    LocalDate getExpirationDate();

    BigDecimal getCost();

    String getDescription();
}
