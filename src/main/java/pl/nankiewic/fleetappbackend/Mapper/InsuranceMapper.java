package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nankiewic.fleetappbackend.DTO.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumInsuranceType;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.Repository.InsuranceTypeRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class InsuranceMapper {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private InsuranceTypeRepository insuranceTypeRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "insuranceType", ignore = true)
    public abstract VehicleInsurance insuranceDtoToVehicleInsuranceEntity(InsuranceRequestDTO insuranceRequestDTO);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleInsuranceAddAttribute(InsuranceRequestDTO insuranceRequestDTO, @MappingTarget VehicleInsurance vehicleInsurance) {
        vehicleInsurance.setVehicle(vehicleRepository.findById(insuranceRequestDTO.getVehicleId()).orElseThrow(
                () -> new EntityNotFoundException("Vehicle not found")));

        if (EnumInsuranceType.AC.name().equals(insuranceRequestDTO.getInsuranceType())) {
            vehicleInsurance.setInsuranceType(insuranceTypeRepository.findByEnumName(EnumInsuranceType.AC));
        } else if (EnumInsuranceType.OC.name().equals(insuranceRequestDTO.getInsuranceType())) {
            vehicleInsurance.setInsuranceType(insuranceTypeRepository.findByEnumName(EnumInsuranceType.OC));
        } else if (EnumInsuranceType.NNW.name().equals(insuranceRequestDTO.getInsuranceType())) {
            vehicleInsurance.setInsuranceType(insuranceTypeRepository.findByEnumName(EnumInsuranceType.NNW));
        } else throw new EntityNotFoundException("Insurance type not found");

    }

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "insuranceType", ignore = true)
    public abstract void updateVehicleInsuranceFromDto(@MappingTarget VehicleInsurance vehicleInsurance, InsuranceRequestDTO insuranceRequestDTO);

}
