package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import pl.nankiewic.fleetappbackend.dto.use.UseDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleUse;

@Mapper(componentModel = "spring")
public abstract class UseMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract VehicleUse vehicleUseDtoToEntity(UseDTO useDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleUseAddAttribute(UseDTO useDTO, @MappingTarget VehicleUse vehicleUse) {
        var vehicle = Vehicle.builder()
                .id(useDTO.getVehicleId())
                .build();
        var user = User.builder()
                .id(useDTO.getUserId())
                .build();

        vehicleUse.setUser(user);
        vehicleUse.setVehicle(vehicle);
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract void updateVehicleUseFromDto(@MappingTarget VehicleUse vehicleUse, UseDTO useDTO);

}