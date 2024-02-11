package pl.nankiewic.fleetappbackend.util.vehicle;

import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleMake;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleStatus;

import java.math.BigDecimal;

public class VehicleRequestResponseDTOFactory {

    public static VehicleDTO buildVehicleRequestResponseDTO() {
        return VehicleDTO.builder()
                .make(VehicleMake.SKODA)
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

    public static VehicleDTO buildVehicleRequestResponseDTO(Long id) {
        return VehicleDTO.builder()
                .id(id)
                .make(VehicleMake.SKODA)
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


    public static VehicleDTO buildVehicleRequestResponseDTO(VehicleMake make, String model) {
        return VehicleDTO.builder()
                .make(make)
                .model(model)
                .vehicleStatus(VehicleStatus.ACTIVE)
                .build();
    }

    public static VehicleDTO buildVehicleRequestResponseDTO(VehicleMake make, String model, VehicleStatus status) {
        return VehicleDTO.builder()
                .make(make)
                .model(model)
                .vehicleStatus(status)
                .build();
    }

}
