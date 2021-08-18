package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.InsuranceDTO;
import pl.nankiewic.fleetappbackend.Entity.InsuranceType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface InsuranceMapper {

    @Mapping(target = "vehicleId", source = "vehicle")
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

    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "insuranceType", ignore = true)
    VehicleInsurance insuranceDTOtoVehicleInsurance(final InsuranceDTO insuranceDTO);

    Iterable<InsuranceDTO> vehicleInsurancesToInsurancesDTO(Iterable<VehicleInsurance> vehicleInsurance);
}
