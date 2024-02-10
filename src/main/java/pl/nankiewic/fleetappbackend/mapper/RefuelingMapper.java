package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nankiewic.fleetappbackend.dto.RefuelingDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public abstract class RefuelingMapper {

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract VehicleRefueling refuelingDtoToVehicleRefuelingEntity(RefuelingDTO refuelingDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleRefuelingAddAttribute(RefuelingDTO refuelingDTO,
                                             @MappingTarget VehicleRefueling vehicleRefueling) {
        vehicleRefueling.setVehicle(vehicleRepository.findById(refuelingDTO.getVehicleId()).orElseThrow(
                () -> new EntityNotFoundException("Vehicle not found")));
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract void updateVehicleRepairFromDto(@MappingTarget VehicleRefueling vehicleRefueling,
                                                    RefuelingDTO refuelingDTO);

}
