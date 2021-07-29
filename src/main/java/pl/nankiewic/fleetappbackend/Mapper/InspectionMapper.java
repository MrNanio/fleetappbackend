package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.nankiewic.fleetappbackend.DTO.InspectionDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInspection;

@Mapper(componentModel = "spring")
public interface InspectionMapper {
    @Mappings({
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
            @Mapping(target = "vehicle", ignore = true),
    })
    VehicleInspection inspectionDTOtoVehicleInspection(final InspectionDTO inspectionDTO);
    Iterable<InspectionDTO> vehicleInspectionsToInspectionsDTO(Iterable<VehicleInspection> vehicleInspections);
}
