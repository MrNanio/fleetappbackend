package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class UseMapper {

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract VehicleUse vehicleUseDtoToEntity(UseDTO useDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleUseAddAttribute(UseDTO useDTO, @MappingTarget VehicleUse vehicleUse) {
        vehicleUse.setVehicle(vehicleRepository.findById(useDTO.getVehicleId()).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: użycie")));
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract void updateVehicleUseFromDto(@MappingTarget VehicleUse vehicleUse, UseDTO useDTO);

}
