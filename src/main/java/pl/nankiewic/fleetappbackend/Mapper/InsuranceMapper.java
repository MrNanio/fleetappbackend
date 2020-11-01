package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.InsuranceDTO;
import pl.nankiewic.fleetappbackend.Entity.InsuranceType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface InsuranceMapper {
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="effectiveDate", source="effectiveDate"),
            @Mapping(target="expirationDate", source="expirationDate"),
            @Mapping(target="cost", source="cost"),
            @Mapping(target="policyNumber", source="policyNumber"),
            @Mapping(target="description", source="description"),
            @Mapping(target = "vehicleId", source = "vehicle"),
            @Mapping(target = "insuranceType", source = "insuranceType"),
    })
    InsuranceDTO vehicleInsuranceToInsuranceDTO(final VehicleInsurance insurance);
    default Long vehicleToId(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return vehicle.getId();
    }
    default String typeToName(InsuranceType insuranceType) {
        if (insuranceType == null) {
            return null;
        }
        return insuranceType.getName();
    }
    @Mappings({
            @Mapping(target="id", source = "id"),
            @Mapping(target="effectiveDate", source="effectiveDate"),
            @Mapping(target="expirationDate", source="expirationDate"),
            @Mapping(target="cost", source="cost"),
            @Mapping(target="policyNumber", source="policyNumber"),
            @Mapping(target="description", source="description"),
            @Mapping(target = "vehicle", ignore = true),
            @Mapping(target = "insuranceType", ignore = true),
    })
    VehicleInsurance insuranceDTOtoVehicleInsurance(final InsuranceDTO insuranceDTO);
    Iterable<InsuranceDTO> vehicleInsurancesToInsurancesDTO(Iterable<VehicleInsurance> vehicleInsurance);
}
