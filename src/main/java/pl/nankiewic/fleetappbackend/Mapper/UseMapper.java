package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {VehicleMapper.class})
public interface UseMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "trip", source = "trip"),
            @Mapping(target = "tripDate", source = "tripDate"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "vehicleId", source = "vehicle")
    })
    UseDTO vehicleUseToUseDTO(final VehicleUse vehicleUse);
    default Long vehicleToId(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return vehicle.getId();
    }
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "trip", source = "trip"),
            @Mapping(target = "tripDate", source = "tripDate"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "vehicle", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    VehicleUse useDTOtoVehicleUse(final UseDTO useDTO);
    Iterable <UseDTO> vehicleUseToUseDTO(Iterable<VehicleUse> vehicleUses);
}
