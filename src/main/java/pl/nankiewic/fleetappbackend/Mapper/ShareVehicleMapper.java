package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nankiewic.fleetappbackend.Entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class ShareVehicleMapper {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract CurrentVehicleUser shareDtoToCurrentVehicleUser(Long vehicleId, Long userId);


    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleRepairAddAttribute(Long vehicleId,
                                          Long userId,
                                          @MappingTarget CurrentVehicleUser currentVehicleUser) {
        currentVehicleUser.setVehicle(vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new EntityNotFoundException("Vehicle not found")));
        currentVehicleUser.setUser(userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")));
    }
}
