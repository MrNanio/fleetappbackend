package pl.nankiewic.fleetappbackend.util.vehicle;

import pl.nankiewic.fleetappbackend.entity.*;

import java.math.BigDecimal;

public class VehicleFactory {

    public static Vehicle buildVehicle(Long userId) {

        var fuelType = FuelType.builder().id(1L).build();
        var vehicleStatus = VehicleStatus.builder().id(1L).build();
        var vehicleMake = VehicleMake.builder().id(1L).build();
        var user = User.builder().id(userId).build();

        return Vehicle.builder()
                .vehicleMake(vehicleMake)
                .user(user)
                .model("FABIA")
                .year("2009")
                .color("BIAŁY")
                .mileage("209000")
                .vinNumber("GTFRED4567DEY65TG")
                .vehicleRegistrationNumber("LU7654D")
                .fuelType(fuelType)
                .cityFuelConsumption(BigDecimal.valueOf(5.6))
                .countryFuelConsumption(BigDecimal.valueOf(3.6))
                .averageFuelConsumption(BigDecimal.valueOf(4.6))
                .vehicleStatus(vehicleStatus)
                .build();
    }
}
