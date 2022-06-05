package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nankiewic.fleetappbackend.DTO.InspectionDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleInspection;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class InspectionMapper {

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleInspection vehicleInspectionDtoToEntity(InspectionDTO inspectionDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleInspectionAddAttribute(InspectionDTO inspectionDTO,
                                              @MappingTarget VehicleInspection vehicleInspection) {
        vehicleInspection.setVehicle(vehicleRepository.findById(inspectionDTO.getVehicleId()).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: pojazd")));
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract void updateVehicleInspectionFromDto(@MappingTarget VehicleInspection vehicleInspection,
                                                        InspectionDTO inspectionDTO);

}
