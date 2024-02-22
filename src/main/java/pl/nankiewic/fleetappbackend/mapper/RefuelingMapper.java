package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public abstract class RefuelingMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract VehicleRefueling refuelingDtoToVehicleRefuelingEntity(RefuelingDTO refuelingDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleRefuelingAddAttribute(RefuelingDTO refuelingDTO,
                                             @MappingTarget VehicleRefueling vehicleRefueling) {
        var vehicle = Vehicle.builder()
                .id(refuelingDTO.getVehicleId())
                .build();
        vehicleRefueling.setVehicle(vehicle);
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract void updateVehicleRepairFromDto(@MappingTarget VehicleRefueling vehicleRefueling,
                                                    RefuelingDTO refuelingDTO);

}
