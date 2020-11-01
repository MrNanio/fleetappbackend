package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.InspectionDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInspection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface InspectionMapper {
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="inspectionDate", source="inspectionDate"),
            @Mapping(target="expirationDate", source="expirationDate"),
            @Mapping(target="cost", source="cost"),
            @Mapping(target="description", source="description"),
            @Mapping(target = "vehicleId", source = "vehicle"),
    })
    InspectionDTO vehicleInspectionToInspectionDTO(final VehicleInspection inspection);
    default Long vehicleToId(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return vehicle.getId();
    }
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="inspectionDate", source="inspectionDate"),
            @Mapping(target="expirationDate", source="expirationDate"),
            @Mapping(target="cost", source="cost"),
            @Mapping(target="description", source="description"),
            @Mapping(target = "vehicle", ignore = true),
    })
    VehicleInspection inspectionDTOtoVehicleInspection(final InspectionDTO inspectionDTO);
    Iterable<InspectionDTO> vehicleInspectionsToInspectionsDTO(Iterable<VehicleInspection> vehicleInspections);
}
