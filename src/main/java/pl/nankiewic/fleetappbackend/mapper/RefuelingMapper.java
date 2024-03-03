package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingModifyDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public abstract class RefuelingMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract VehicleRefueling refuelingDtoToVehicleRefuelingEntity(RefuelingModifyDTO refuelingModifyDTO);

    @BeanMapping(qualifiedByName = "entityToDto")
    @Mapping(target = "vehicleId", source = "vehicle.id" )
    @Mapping(target = "userId", source = "user.id")
    public abstract RefuelingModifyDTO refuelingToRefuelingModify(VehicleRefueling vehicleRefueling);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleRefuelingAddAttribute(RefuelingModifyDTO refuelingModifyDTO,
                                             @MappingTarget VehicleRefueling vehicleRefueling) {
        var vehicle = Vehicle.builder()
                .id(refuelingModifyDTO.getVehicleId())
                .build();
        vehicleRefueling.setVehicle(vehicle);
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract VehicleRefueling updateVehicleRepairFromDto(@MappingTarget VehicleRefueling vehicleRefueling,
                                                    RefuelingModifyDTO refuelingModifyDTO);

}
