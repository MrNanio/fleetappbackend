package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {VehicleMapper.class})
public interface RefuelingMapper {

    @Mapping(target = "vehicleId", source = "vehicle")
    @Mapping(target = "userId", source = "user")
    RefuelingDTO refuelingToRefuelingDTO(final VehicleRefueling refueling);

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
    VehicleRefueling refuelingDTOToRefueling(final RefuelingDTO refuelingDTO);

    Iterable<RefuelingDTO> refuelingToRefuelingDTO(Iterable<VehicleRefueling> refueling);
}
