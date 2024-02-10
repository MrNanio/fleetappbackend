package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.repository.VehicleMakeRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VehicleMapper {

    @Autowired
    private VehicleMakeRepository vehicleMakeRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "vehicleMake", ignore = true),
            @Mapping(target = "currentVehicleUser", ignore = true),
            @Mapping(target = "refuelings", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "vehicleInspections", ignore = true),
            @Mapping(target = "vehicleInsurances", ignore = true),
            @Mapping(target = "vehicleRepairs", ignore = true),
            @Mapping(target = "vehicleUses", ignore = true)
    })
    public abstract Vehicle vehicleDTOtoVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO, User user);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleValueMapToEnum(VehicleRequestResponseDTO vehicleRequestResponseDTO, @MappingTarget Vehicle vehicle, User user) {
        vehicle.setVehicleMake(vehicleMakeRepository.findByName(vehicleRequestResponseDTO.getMake()));
        vehicle.setUser(user);
    }

    @BeanMapping(qualifiedByName = "dtoToResponseDto")
    public abstract VehicleRequestResponseDTO vehicleDTOtoVehicleResponseDTO(VehicleDTO vehicleDTO);

    public abstract List<VehicleRequestResponseDTO> vehiclesDTOtoVehiclesResponseDTO(List<VehicleDTO> vehiclesDTO);

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mappings({
            @Mapping(target = "vehicleMake", ignore = true),
            @Mapping(target = "vehicleStatus", ignore = true),
            @Mapping(target = "fuelType", ignore = true),
            @Mapping(target = "currentVehicleUser", ignore = true),
            @Mapping(target = "refuelings", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "vehicleInspections", ignore = true),
            @Mapping(target = "vehicleInsurances", ignore = true),
            @Mapping(target = "vehicleRepairs", ignore = true),
            @Mapping(target = "vehicleUses", ignore = true)
    })
    public abstract void updateVehicleFromRequest(@MappingTarget Vehicle vehicle, VehicleRequestResponseDTO vehicleRequestResponseDTO);

    @BeanMapping(qualifiedByName = "entityToResponseDto")
    @Mappings({
            @Mapping(target = "make", ignore = true),
            @Mapping(target = "vehicleStatus", ignore = true),
            @Mapping(target = "fuelType", ignore = true),
    })
    public abstract VehicleRequestResponseDTO entityToResponseDto(Vehicle vehicle);

    @Named("entityToResponseDto")
    @AfterMapping
    public void addAttributeToMap(Vehicle vehicle, @MappingTarget VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        vehicleRequestResponseDTO.setMake(vehicle.getVehicleMake().getName());
    }

}
