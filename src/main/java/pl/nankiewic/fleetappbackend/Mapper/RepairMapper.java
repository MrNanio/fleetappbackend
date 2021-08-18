package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RepairMapper {

    @Mapping(target = "vehicleId", source = "vehicle")
    RepairDTO vehicleRepairToRepairDTO(final VehicleRepair repair);

    default Long vehicleToId(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return vehicle.getId();
    }

    @Mapping(target = "vehicle", ignore = true)
    VehicleRepair repairDTOtoVehicleRepair(final RepairDTO repairDTO);

    Iterable<RepairDTO> vehicleRepairsToRepairsDTO(Iterable<VehicleRepair> vehicleRepairs);
}
