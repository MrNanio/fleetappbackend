package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import pl.nankiewic.fleetappbackend.dto.repair.RepairModifyDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleRepair;

@Mapper(componentModel = "spring")
public abstract class RepairMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleRepair repairDtoToVehicleRepairEntity(RepairModifyDTO repairModifyDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleRepairAddAttribute(RepairModifyDTO repairModifyDTO, @MappingTarget VehicleRepair vehicleRepair) {
        var vehicle = Vehicle.builder()
                .id(repairModifyDTO.getVehicleId())
                .build();
        vehicleRepair.setVehicle(vehicle);
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleRepair updateVehicleRepairFromDto(@MappingTarget VehicleRepair vehicleRepair, RepairModifyDTO repairModifyDTO);

    @BeanMapping(qualifiedByName = "entityToDto")
    @Mapping(target = "vehicleId", source = "vehicle.id")
    public abstract RepairModifyDTO vehicleRepairToRepairModifyDTO(VehicleRepair vehicleRepair);

}
