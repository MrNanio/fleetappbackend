package pl.nankiewic.fleetappbackend.dto.use;

import java.time.LocalDate;

public interface UseView {

    Long getId();

    Long getVehicleId();

    Long getUserId();

    Short getTrip();

    LocalDate getTripDate();

    String getTripType();

    String getDescription();

}