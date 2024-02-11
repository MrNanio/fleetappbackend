package pl.nankiewic.fleetappbackend.util.vehicle;

import pl.nankiewic.fleetappbackend.entity.*;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleStatus;

import java.math.BigDecimal;

import static pl.nankiewic.fleetappbackend.entity.enums.VehicleMake.SKODA;

public class VehicleFactory {

    public static Vehicle buildVehicle(Long userId) {

        var user = User.builder()
                .id(userId)
                .build();

        return Vehicle.builder()
                .make(SKODA)
                .user(user)
                .model("FABIA")
                .year("2009")
                .color("BIAŁY")
                .mileage("209000")
                .vinNumber("GTFRED4567DEY65TG")
                .vehicleRegistrationNumber("LU7654D")
                .fuelType(FuelType.ON)
                .cityFuelConsumption(BigDecimal.valueOf(5.6))
                .countryFuelConsumption(BigDecimal.valueOf(3.6))
                .averageFuelConsumption(BigDecimal.valueOf(4.6))
                .vehicleStatus(VehicleStatus.ACTIVE)
                .build();
    }
}
