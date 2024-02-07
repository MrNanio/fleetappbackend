package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumFuelType;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumVehicleStatus;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.repository.FuelTypeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleMakeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleStatusRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class VehicleMapper {
    @Autowired
    private VehicleStatusRepository vehicleStatusRepository;
    @Autowired
    private FuelTypeRepository fuelTypeRepository;
    @Autowired
    private VehicleMakeRepository vehicleMakeRepository;

    @BeanMapping(qualifiedByName = "dtoToEntity")
    @Mappings({
            @Mapping(target = "id", ignore = true),
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
    public abstract Vehicle vehicleDTOtoVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO, User user);

    @Named(value = "dtoToEntity")
    @AfterMapping
    public void vehicleValueMapToEnum(VehicleRequestResponseDTO vehicleRequestResponseDTO, @MappingTarget Vehicle vehicle, User user) {

        if (EnumVehicleStatus.ACTIVE.name().equals(vehicleRequestResponseDTO.getVehicleStatus())) {
            vehicle.setVehicleStatus(vehicleStatusRepository.findByEnumName(EnumVehicleStatus.ACTIVE));
        } else if (EnumVehicleStatus.REPAIR.name().equals(vehicleRequestResponseDTO.getVehicleStatus())) {
            vehicle.setVehicleStatus(vehicleStatusRepository.findByEnumName(EnumVehicleStatus.ACTIVE));
        } else throw new EntityNotFoundException("Vehicle Status not found");

        if (EnumFuelType.LPG.name().equals(vehicleRequestResponseDTO.getFuelType())) {
            vehicle.setFuelType(fuelTypeRepository.findByEnumName(EnumFuelType.LPG));
        } else if (EnumFuelType.ON.name().equals(vehicleRequestResponseDTO.getFuelType())) {
            vehicle.setFuelType(fuelTypeRepository.findByEnumName(EnumFuelType.ON));
        } else if (EnumFuelType.PB95.name().equals(vehicleRequestResponseDTO.getFuelType())) {
            vehicle.setFuelType(fuelTypeRepository.findByEnumName(EnumFuelType.PB95));
        } else if (EnumFuelType.PB98.name().equals(vehicleRequestResponseDTO.getFuelType())) {
            vehicle.setFuelType(fuelTypeRepository.findByEnumName(EnumFuelType.PB98));
        }
        vehicle.setVehicleMake(vehicleMakeRepository.findByName(vehicleRequestResponseDTO.getMake()));
        vehicle.setUser(user);
    }

    @BeanMapping(qualifiedByName = "dtoToResponseDto")
    @Mapping(target = "vehicleStatus", ignore = true)
    @Mapping(target = "fuelType", ignore = true)
    public abstract VehicleRequestResponseDTO vehicleDTOtoVehicleResponseDTO(VehicleDTO vehicleDTO);

    public abstract Iterable<VehicleRequestResponseDTO> vehiclesDTOtoVehiclesResponseDTO(Iterable<VehicleDTO> vehiclesDTO);

    @Named("dtoToResponseDto")
    @AfterMapping
    public void vehicleValueMapToString(VehicleDTO vehicleDTO, @MappingTarget VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        vehicleRequestResponseDTO.setVehicleStatus(vehicleDTO.getVehicleStatus().name());
        vehicleRequestResponseDTO.setFuelType(vehicleDTO.getFuelType().name());
    }

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
        vehicleRequestResponseDTO.setVehicleStatus(vehicle.getVehicleStatus().getVehicleStatus().name());
        vehicleRequestResponseDTO.setFuelType(vehicle.getFuelType().getFuelType().name());
        vehicleRequestResponseDTO.setMake(vehicle.getVehicleMake().getName());
    }

}
