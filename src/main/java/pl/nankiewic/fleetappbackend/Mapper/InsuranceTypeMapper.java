package pl.nankiewic.fleetappbackend.Mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO;
import pl.nankiewic.fleetappbackend.Entity.InsuranceType;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {VehicleMapper.class})
public interface InsuranceTypeMapper {

    InsuranceTypeDTO typeToTypeDTO(final InsuranceType insuranceType);

    Iterable<InsuranceTypeDTO> typesToTypesDTO(Iterable<InsuranceType> insuranceTypes);

    @Mapping(target = "vehicleInsurances", ignore = true)
    InsuranceType typeDTOToType(final InsuranceTypeDTO insuranceTypeDTO);
}
