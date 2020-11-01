package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.FuelType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;
import pl.nankiewic.fleetappbackend.Entity.VehicleStatus;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VehicleMapper {
    @Mappings({
            @Mapping(target="id", source = "id"),
            @Mapping(target="make", source = "vehicleMake"),
            @Mapping(target="model", source="model"),
            @Mapping(target="year", source="year"),
            @Mapping(target="color", source="color"),
            @Mapping(target="mileage", source="mileage"),
            @Mapping(target="vinNumber", source="vinNumber"),
            @Mapping(target="vehicleRegistrationNumber", source="vehicleRegistrationNumber"),
            @Mapping(target ="vehicleStatus", source = "vehicleStatus"),
            @Mapping(target ="fuelType", source = "fuelType"),
            @Mapping(target="cityFuelConsumption", source="cityFuelConsumption"),
            @Mapping(target="countryFuelConsumption", source="countryFuelConsumption"),
            @Mapping(target="averageFuelConsumption", source="averageFuelConsumption"),
    })
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
        return fuelType.getName();
    }
    default String vehicleStatusToString(VehicleStatus vehicleStatus) {
        if (vehicleStatus == null) {
            return null;
        }
        return vehicleStatus.getStatusName();
    }
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "vehicleMake", ignore = true),
            @Mapping(target = "model", source = "model"),
            @Mapping(target = "year", source = "year"),
            @Mapping(target = "color", source = "color"),
            @Mapping(target = "mileage", source = "mileage"),
            @Mapping(target = "vinNumber", source = "vinNumber"),
            @Mapping(target = "vehicleRegistrationNumber", source = "vehicleRegistrationNumber"),
            @Mapping(target = "vehicleStatus", ignore = true),
            @Mapping(target = "fuelType", ignore = true),
            @Mapping(target = "cityFuelConsumption", source = "cityFuelConsumption"),
            @Mapping(target = "countryFuelConsumption", source = "countryFuelConsumption"),
            @Mapping(target = "averageFuelConsumption", source = "averageFuelConsumption"),

    })
    Vehicle vehicleDTOtoVehicle(final VehicleDTO vehicleDTO);
    default VehicleMake makeStringToVehicleMake(String make) {
        if (make == null) {
            return null;
        }
        VehicleMake vehicleMake=new VehicleMake();
        vehicleMake.setName(make);
        return vehicleMake;
    }
    Iterable<VehicleDTO> map(Iterable<Vehicle> vehicles);

}
