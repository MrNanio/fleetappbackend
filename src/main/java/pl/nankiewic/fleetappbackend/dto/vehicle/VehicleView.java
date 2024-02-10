package pl.nankiewic.fleetappbackend.dto.vehicle;

import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleStatus;

import java.math.BigDecimal;

public interface VehicleView {

    Long getId();

    String getMake();

    String getModel();

    String getYear();

    String getColor();

    String getMileage();

    String getVinNumber();

    String getVehicleRegistrationNumber();

    FuelType getFuelType();

    BigDecimal getCityFuelConsumption();

    BigDecimal getCountryFuelConsumption();

    BigDecimal getAverageFuelConsumption();

    VehicleStatus getVehicleStatus();

}
