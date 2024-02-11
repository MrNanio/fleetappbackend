package pl.nankiewic.fleetappbackend.dto.vehicle;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleMake;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleStatus;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
public class VehicleBaseDTO {

    @NotNull
    private VehicleMake make;

    @NotNull
    private String model;

    @NotNull
    private String year;

    @NotNull
    private String color;

    @NotNull
    private String mileage;

    @NotNull
    private String vinNumber;

    @NotNull
    private String vehicleRegistrationNumber;

    @NotNull
    private FuelType fuelType;

    @NotNull
    private BigDecimal cityFuelConsumption;

    @NotNull
    private BigDecimal countryFuelConsumption;

    @NotNull
    private BigDecimal averageFuelConsumption;

    @NotNull
    private VehicleStatus vehicleStatus;

}
