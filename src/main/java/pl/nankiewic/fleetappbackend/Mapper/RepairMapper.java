package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RepairMapper {
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="repairDate", source="repairDate"),
            @Mapping(target="description", source="description"),
            @Mapping(target="cost", source="cost"),
            @Mapping(target="title", source="title"),
            @Mapping(target = "vehicleId", source = "vehicle"),
    })
    RepairDTO vehicleRepairToRepairDTO(final VehicleRepair repair);
    default Long vehicleToId(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return vehicle.getId();
    }
    @Mappings({
            @Mapping(target="id", source = "id"),
            @Mapping(target="repairDate", source="repairDate"),
            @Mapping(target="description", source="description"),
            @Mapping(target="cost", source="cost"),
            @Mapping(target="title", source="title"),
            @Mapping(target = "vehicle", ignore = true),
    })
    VehicleRepair repairDTOtoVehicleRepair(final RepairDTO repairDTO);
    Iterable<RepairDTO> vehicleRepairsToRepairsDTO(Iterable<VehicleRepair> vehicleRepairs);
}
