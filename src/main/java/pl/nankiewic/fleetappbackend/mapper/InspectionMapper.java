package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionModifyDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleInspection;

@Mapper(componentModel = "spring")
public abstract class InspectionMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleInspection vehicleInspectionDtoToEntity(InspectionModifyDTO inspectionModifyDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleInspectionAddAttribute(InspectionModifyDTO inspectionModifyDTO,
                                              @MappingTarget VehicleInspection vehicleInspection) {
        var vehicle = Vehicle.builder()
                .id(inspectionModifyDTO.getVehicleId())
                .build();
        vehicleInspection.setVehicle(vehicle);
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleInspection updateVehicleInspectionFromDto(@MappingTarget VehicleInspection vehicleInspection,
                                                        InspectionModifyDTO inspectionModifyDTO);

    @BeanMapping(qualifiedByName = "entityToDto")
    @Mapping(target = "vehicleId", source = "vehicle.id")
    public abstract InspectionModifyDTO vehicleInspectionToDto(VehicleInspection vehicleInspection);

}