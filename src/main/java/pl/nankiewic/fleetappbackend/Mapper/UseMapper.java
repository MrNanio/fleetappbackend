package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {VehicleMapper.class})
public interface UseMapper {

    @Mapping(target = "vehicleId", source = "vehicle")
    @Mapping(target = "userId", source = "user")
    UseDTO vehicleUseToUseDTO(final VehicleUse vehicleUse);

    default Long vehicleToId(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return vehicle.getId();
    }

    default Long userToId(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    VehicleUse useDTOtoVehicleUse(final UseDTO useDTO);

    Iterable<UseDTO> vehicleUseToUseDTO(Iterable<VehicleUse> vehicleUses);
}
