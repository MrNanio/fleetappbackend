package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import pl.nankiewic.fleetappbackend.dto.repair.RepairDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleRepair;

@Mapper(componentModel = "spring")
public abstract class RepairMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleRepair repairDtoToVehicleRepairEntity(RepairDTO repairDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleRepairAddAttribute(RepairDTO repairDTO, @MappingTarget VehicleRepair vehicleRepair) {
        var vehicle = Vehicle.builder()
                .id(repairDTO.getVehicleId())
                .build();
        vehicleRepair.setVehicle(vehicle);
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract void updateVehicleRepairFromDto(@MappingTarget VehicleRepair vehicleRepair, RepairDTO repairDTO);

}
