package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import pl.nankiewic.fleetappbackend.entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

@Mapper(componentModel = "spring")
public abstract class ShareVehicleMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract CurrentVehicleUser shareDtoToCurrentVehicleUser(Long vehicleId, Long userId);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleShareAddAttribute(Long vehicleId,
                                         Long userId,
                                         @MappingTarget CurrentVehicleUser currentVehicleUser) {
        var vehicle = Vehicle.builder()
                .id(vehicleId)
                .build();
        var user = User.builder()
                .id(userId)
                .build();

        currentVehicleUser.setUser(user);
        currentVehicleUser.setVehicle(vehicle);
    }

}
