package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.VehicleMake;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.exceptions.UserNotFoundException;
import pl.nankiewic.fleetappbackend.mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleMakeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMakeRepository vehicleMakeRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public VehicleRequestResponseDTO createVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(authentication.getName())
                .map(user -> vehicleMapper.vehicleDTOtoVehicle(vehicleRequestResponseDTO, user))
                .map(vehicleRepository::save)
                .map(vehicleMapper::entityToResponseDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public void updateVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        validPermissionToObject(vehicleRequestResponseDTO.getId());
        var vehicle = vehicleRepository.findById(vehicleRequestResponseDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: pojazd"));
        vehicleMapper.updateVehicleFromRequest(vehicle, vehicleRequestResponseDTO);
        vehicleRepository.save(vehicle);
    }

    public List<VehicleView> getVehiclesDataByUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return vehicleRepository.findVehiclesDataByUser(authentication.getName());
    }

    public Optional<VehicleView> getVehicleDataById(Long id) {
        validPermissionToObject(id);
        return vehicleRepository.findVehicleDetailsById(id);
    }

    public void deleteVehicleById(Long id) {
        validPermissionToObject(id);
        vehicleRepository.deleteById(id);
    }

    public List<VehicleMake> getMake() {
        return vehicleMakeRepository.findAll();
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
}
