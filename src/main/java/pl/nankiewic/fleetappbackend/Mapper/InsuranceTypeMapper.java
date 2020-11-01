package pl.nankiewic.fleetappbackend.Mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO;
import pl.nankiewic.fleetappbackend.Entity.InsuranceType;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {VehicleMapper.class})
public interface InsuranceTypeMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),

    })
    InsuranceTypeDTO typeToTypeDTO(final InsuranceType insuranceType);
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "vehicleInsurances", ignore = true)
    })
    InsuranceType typeDTOToType(final InsuranceTypeDTO insuranceTypeDTO);
    Iterable<InsuranceTypeDTO> typesToTypesDTO(Iterable<InsuranceType> insuranceTypes);
}
