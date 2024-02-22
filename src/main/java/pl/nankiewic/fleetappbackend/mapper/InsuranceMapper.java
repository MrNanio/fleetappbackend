package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceModifyDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;

@Mapper(componentModel = "spring")
public abstract class InsuranceMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleInsurance insuranceDtoToVehicleInsuranceEntity(InsuranceModifyDTO insuranceModifyDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleInsuranceAddAttribute(InsuranceModifyDTO insuranceModifyDTO,
                                             @MappingTarget VehicleInsurance vehicleInsurance) {
        var vehicle = Vehicle.builder()
                .id(insuranceModifyDTO.getVehicleId())
                .build();
        vehicleInsurance.setVehicle(vehicle);
    }

    @BeanMapping(qualifiedByName = "entityToDto")
    @Mapping(target = "vehicleId", source = "vehicle.id")
    public abstract InsuranceModifyDTO insuranceToInsuranceModifyDTO(VehicleInsurance vehicleInsurance);

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "insuranceType", ignore = true)
    public abstract VehicleInsurance updateVehicleInsuranceFromDto(@MappingTarget VehicleInsurance vehicleInsurance,
                                                                   InsuranceModifyDTO insuranceModifyDTO);

}