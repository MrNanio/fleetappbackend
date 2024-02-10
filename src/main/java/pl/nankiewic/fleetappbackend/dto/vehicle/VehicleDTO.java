package pl.nankiewic.fleetappbackend.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleStatus;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VehicleDTO {

    @NotNull
    private Long id;
    @NotNull
    private String make;
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
