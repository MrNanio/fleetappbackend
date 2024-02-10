package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class InsuranceMapper {

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    public abstract VehicleInsurance insuranceDtoToVehicleInsuranceEntity(InsuranceRequestDTO insuranceRequestDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleInsuranceAddAttribute(InsuranceRequestDTO insuranceRequestDTO, @MappingTarget VehicleInsurance vehicleInsurance) {
        vehicleInsurance.setVehicle(vehicleRepository.findById(insuranceRequestDTO.getVehicleId()).orElseThrow(
                () -> new EntityNotFoundException("Vehicle not found")));
    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "insuranceType", ignore = true)
    public abstract void updateVehicleInsuranceFromDto(@MappingTarget VehicleInsurance vehicleInsurance, InsuranceRequestDTO insuranceRequestDTO);

}
