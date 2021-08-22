package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.FuelType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;
import pl.nankiewic.fleetappbackend.Entity.VehicleStatus;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "make", source = "vehicleMake")
    VehicleDTO vehicleToVehicleDTO(final Vehicle vehicle);

    default String vehicleMakeToString(VehicleMake vehicleMake) {
        if (vehicleMake == null) {
            return null;
        }
        return vehicleMake.getName();
    }

    default String fuelTypeToString(FuelType fuelType) {
        if (fuelType == null) {
            return null;
        }
        return fuelType.getFuelType().name();
    }

    default String vehicleStatusToString(VehicleStatus vehicleStatus) {
        if (vehicleStatus == null) {
            return null;
        }
        return vehicleStatus.getVehicleStatus().name();
    }

    @Mappings({
            @Mapping(target = "vehicleMake", ignore = true),
            @Mapping(target = "vehicleStatus", ignore = true),
            @Mapping(target = "fuelType", ignore = true),
            @Mapping(target = "currentVehicleUser", ignore = true),
            @Mapping(target = "refuelings", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "vehicleInspections", ignore = true),
            @Mapping(target = "vehicleInsurances", ignore = true),
            @Mapping(target = "vehicleRepairs", ignore = true),
            @Mapping(target = "vehicleUses", ignore = true)
    })
    Vehicle vehicleDTOtoVehicle(final VehicleDTO vehicleDTO);

    Iterable<VehicleDTO> map(Iterable<Vehicle> vehicles);

}
