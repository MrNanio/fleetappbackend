package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class RepairMapper {
    @Autowired
    private VehicleRepository vehicleRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleRepair repairDtoToVehicleRepairEntity(RepairDTO repairDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleRepairAddAttribute(RepairDTO repairDTO, @MappingTarget VehicleRepair vehicleRepair) {
        vehicleRepair.setVehicle(vehicleRepository.findById(repairDTO.getVehicleId()).orElseThrow(
                () -> new EntityNotFoundException("Vehicle not found")));
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract void updateVehicleRepairFromDto(@MappingTarget VehicleRepair vehicleRepair, RepairDTO repairDTO);

}
