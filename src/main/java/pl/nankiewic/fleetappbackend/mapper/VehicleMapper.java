package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;

import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleBaseDTO;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VehicleMapper {

    @BeanMapping(qualifiedByName = "dtoToEntity")
            @Mapping(target = "id", ignore = true)
            @Mapping(target = "currentVehicleUser", ignore = true)
            @Mapping(target = "refuelings", ignore = true)
            @Mapping(target = "user", ignore = true)
            @Mapping(target = "vehicleInspections", ignore = true)
            @Mapping(target = "vehicleInsurances", ignore = true)
            @Mapping(target = "vehicleRepairs", ignore = true)
            @Mapping(target = "vehicleUses", ignore = true)
    public abstract Vehicle vehicleDTOtoVehicle(VehicleBaseDTO vehicleBaseDTO);

    @BeanMapping(qualifiedByName = "dtoToResponseDto")
    public abstract VehicleDTO vehicleDTOtoVehicleResponseDTO(VehicleBaseDTO vehicleBaseDTO);

    public abstract List<VehicleDTO> vehiclesDTOtoVehiclesResponseDTO(List<VehicleBaseDTO> vehiclesDTO);

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mapping(target = "vehicleStatus", ignore = true)
    @Mapping(target = "fuelType", ignore = true)
    @Mapping(target = "currentVehicleUser", ignore = true)
    @Mapping(target = "refuelings", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "vehicleInspections", ignore = true)
    @Mapping(target = "vehicleInsurances", ignore = true)
    @Mapping(target = "vehicleRepairs", ignore = true)
    @Mapping(target = "vehicleUses", ignore = true)
    public abstract Vehicle updateVehicleFromRequest(@MappingTarget Vehicle vehicle, VehicleDTO vehicleDTO);

    @BeanMapping(qualifiedByName = "entityToResponseDto")
    public abstract VehicleDTO entityToDto(Vehicle vehicle);

}
