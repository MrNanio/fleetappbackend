package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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
import pl.nankiewic.fleetappbackend.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public VehicleDTO createVehicle(VehicleBaseDTO vehicleBaseDTO) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(authentication.getName())
                .orElseThrow();

        return Optional.of(vehicleMapper.vehicleDTOtoVehicle(vehicleBaseDTO))
                .map(v -> mapUser(v, user))
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return vehicleRepository.findVehicleViewsByUser(authentication.getName());
    }

    public Optional<VehicleView> getVehicleDataById(Long id) {
        validPermissionToObject(id);
        return vehicleRepository.findVehicleViewById(id);
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!checkExistAndPermissionComponent.accessToVehicle(authentication.getName(), vehicleId)) {
            throw new PermissionDeniedException();
        }
    }

    private Vehicle mapUser(Vehicle vehicle, User user) {
        vehicle.setUser(user);
        return vehicle;
    }
}
