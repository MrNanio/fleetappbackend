package pl.nankiewic.fleetappbackend.DTO.vehicle;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleRequestResponseDTO {

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
    private String fuelType;
    @NotNull
    private BigDecimal cityFuelConsumption;
    @NotNull
    private BigDecimal countryFuelConsumption;
    @NotNull
    private BigDecimal averageFuelConsumption;
    @NotNull
    private String  vehicleStatus;
}
