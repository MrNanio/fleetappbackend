package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleBaseDTO;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleMake;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.exceptions.UserNotFoundException;
import pl.nankiewic.fleetappbackend.mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public VehicleDTO createVehicle(VehicleBaseDTO vehicleBaseDTO) {

        return Optional.of(vehicleMapper.vehicleDTOtoVehicle(vehicleBaseDTO))
                .map(this::mapVehicleOwner)
                .map(vehicleRepository::save)
                .map(vehicleMapper::entityToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public VehicleDTO updateVehicle(VehicleDTO vehicleDTO) {
        validPermissionToObject(vehicleDTO.getId());

        return vehicleRepository.findById(vehicleDTO.getId())
                .map(v -> vehicleMapper.updateVehicleFromRequest(v, vehicleDTO))
                .map(vehicleRepository::save)
                .map(vehicleMapper::entityToDto)
                .orElseThrow();
    }

    public List<VehicleView> findVehicleViewsByUser() {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleRepository.findVehicleViewsByUser(userId);
    }

    public VehicleView getVehicleDataById(Long id) {
        validPermissionToObject(id);
        return vehicleRepository.findVehicleViewById(id)
                .orElseThrow();
    }

    public void deleteVehicleById(Long id) {
        validPermissionToObject(id);
        vehicleRepository.deleteById(id);
    }

    public List<VehicleMake> getMake() {
        return Arrays.stream(VehicleMake.values())
                .collect(Collectors.toList());
    }

    public Set<FuelType> getFuelType() {
        return Arrays.stream(FuelType.values())
                .collect(Collectors.toSet());
    }

    private void validPermissionToObject(Long vehicleId) {
        if (!checkExistAndPermissionComponent.accessToVehicle(vehicleId)) {
            throw new PermissionDeniedException();
        }
    }

    private Vehicle mapVehicleOwner(Vehicle vehicle) {
        var userId = JWTokenHelper.getJWTUserId();
        var user = User.builder()
                .id(userId)
                .build();

        vehicle.setUser(user);

        return vehicle;
    }

}