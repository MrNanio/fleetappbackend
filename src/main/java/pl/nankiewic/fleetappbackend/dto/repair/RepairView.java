package pl.nankiewic.fleetappbackend.dto.repair;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RepairView {

    Long getId();

    Long getVehicleId();

    String getTitle();

    LocalDate getRepairDate();

    BigDecimal getCost();

    String getDescription();

}
