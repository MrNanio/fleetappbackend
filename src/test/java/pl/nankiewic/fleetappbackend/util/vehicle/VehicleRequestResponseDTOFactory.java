package pl.nankiewic.fleetappbackend.util.vehicle;

import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleRequestResponseDTO;

import java.math.BigDecimal;

public class VehicleRequestResponseDTOFactory {

    public static VehicleRequestResponseDTO buildVehicleRequestResponseDTO() {
        return VehicleRequestResponseDTO.builder()
                .make("SKODA")
                .model("FABIA")
                .year("2009")
                .color("BIAŁY")
                .mileage("209000")
                .vinNumber("GTFRED4567DEY65TG")
                .vehicleRegistrationNumber("LU7654D")
                .fuelType("ON")
                .cityFuelConsumption(BigDecimal.valueOf(5.6))
                .countryFuelConsumption(BigDecimal.valueOf(3.6))
                .averageFuelConsumption(BigDecimal.valueOf(4.6))
                .vehicleStatus("ACTIVE")
                .build();
    }

    public static VehicleRequestResponseDTO buildVehicleRequestResponseDTO(Long id) {
        return VehicleRequestResponseDTO.builder()
                .id(id)
                .make("SKODA")
                .model("FABIA")
                .year("2009")
                .color("BIAŁY")
                .mileage("209000")
                .vinNumber("GTFRED4567DEY65TG")
                .vehicleRegistrationNumber("LU7654D")
                .fuelType("ON")
                .cityFuelConsumption(BigDecimal.valueOf(5.6))
                .countryFuelConsumption(BigDecimal.valueOf(3.6))
                .averageFuelConsumption(BigDecimal.valueOf(4.6))
                .vehicleStatus("ACTIVE")
                .build();
    }


    public static VehicleRequestResponseDTO buildVehicleRequestResponseDTO(String make, String model) {
        return VehicleRequestResponseDTO.builder()
                .make(make)
                .model(model)
                .vehicleStatus("ACTIVE")
                .build();
    }

    public static VehicleRequestResponseDTO buildVehicleRequestResponseDTO(String make, String model, String status) {
        return VehicleRequestResponseDTO.builder()
                .make(make)
                .model(model)
                .vehicleStatus(status)
                .build();
    }

}
