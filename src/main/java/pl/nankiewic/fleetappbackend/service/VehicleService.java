package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.FuelType;
import pl.nankiewic.fleetappbackend.entity.VehicleMake;
import pl.nankiewic.fleetappbackend.exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.exception.UserNotFoundException;
import pl.nankiewic.fleetappbackend.mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.repository.FuelTypeRepository;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleMakeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMakeRepository vehicleMakeRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public VehicleRequestResponseDTO createVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
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

    public List<FuelType> getFuelType() {
        return fuelTypeRepository.findAll();
    }

    private void validPermissionToObject(Long vehicleId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!checkExistAndPermissionComponent.accessToVehicle(authentication.getName(), vehicleId)) {
            throw new PermissionDeniedException();
        }
    }
}
