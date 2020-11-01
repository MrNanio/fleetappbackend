package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.Entity.Refueling;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {VehicleMapper.class})
public interface RefuelingMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "refuelingDate", source = "refuelingDate"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "cost", source = "cost"),
            @Mapping(target = "litre", source = "litre"),
            @Mapping(target = "vehicleId", source = "vehicle"),
            @Mapping(target = "userId", source = "user")
    })
    RefuelingDTO refuelingToRefuelingDTO(final Refueling refueling);
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
    @Mappings({
            @Mapping(target="id", source = "id"),
            @Mapping(target="refuelingDate", source="refuelingDate"),
            @Mapping(target="description", source="description"),
            @Mapping(target="cost", source="cost"),
            @Mapping(target="litre", source="litre"),
            @Mapping(target = "vehicle", ignore = true),
            @Mapping(target = "user", ignore = true),
    })
    Refueling refuelingDTOToRefueling(final RefuelingDTO refuelingDTO);
    Iterable<RefuelingDTO> refuelingToRefuelingDTO(Iterable<Refueling> refueling);
}
