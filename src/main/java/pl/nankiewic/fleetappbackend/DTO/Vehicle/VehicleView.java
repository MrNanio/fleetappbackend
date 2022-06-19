package pl.nankiewic.fleetappbackend.DTO.Vehicle;

import pl.nankiewic.fleetappbackend.entity.Enum.EnumFuelType;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumVehicleStatus;

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

    EnumFuelType getFuelType();

    BigDecimal getCityFuelConsumption();

    BigDecimal getCountryFuelConsumption();

    BigDecimal getAverageFuelConsumption();

    EnumVehicleStatus getVehicleStatus();

}
