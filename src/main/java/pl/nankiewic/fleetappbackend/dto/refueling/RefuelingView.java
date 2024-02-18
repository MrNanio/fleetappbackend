package pl.nankiewic.fleetappbackend.dto.refueling;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RefuelingView {

    Long getId();

    Long getVehicleId();

    Long getUserId();

    LocalDate getRefuelingDate();

    String getLitre();

    BigDecimal getCost();

    String getDescription();

}