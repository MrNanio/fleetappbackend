package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.FuelType;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleMake;
import pl.nankiewic.fleetappbackend.exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.exception.UserNotFoundException;
import pl.nankiewic.fleetappbackend.mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.repository.FuelTypeRepository;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleMakeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
                .map(user -> vehicleMapper.vehicleDTOtoVehicle(vehicleRequestResponseDTO, user))
                .map(vehicleRepository::save)
                .map(vehicleMapper::entityToResponseDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public void updateVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (checkExistAndPermissionComponent.accessToVehicle(auth.getName(), vehicleRequestResponseDTO.getId())) {
            Vehicle vehicle = vehicleRepository.findById(vehicleRequestResponseDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Nie znaleziono zasobu: pojazd"));
            vehicleMapper.updateVehicleFromRequest(vehicle, vehicleRequestResponseDTO);
            vehicleRepository.save(vehicle);
        } else throw new PermissionDeniedException();
    }

    public Iterable<VehicleView> getVehiclesDataByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (vehicleRepository.existsByUser(userRepository.findUserByEmail(auth.getName()))) {
            if (userRepository.existsByEmail(auth.getName())) {
                return vehicleRepository.findVehiclesDataByUser(auth.getName());
            } else throw new EntityNotFoundException("Nie znaleziono użytkownika");
        } else throw new EntityNotFoundException("Nie znaleziono pojazdów użytkownika");
    }

    public Optional<VehicleView> getVehicleDataById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (checkExistAndPermissionComponent.accessToVehicle(auth.getName(), id)) {
            return vehicleRepository.findVehicleDetailsById(id);
        } else throw new PermissionDeniedException();
    }

    public void deleteVehicleById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (checkExistAndPermissionComponent.accessToVehicle(auth.getName(), id)) {
            vehicleRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }

    public Iterable<VehicleMake> getMake() {
        return vehicleMakeRepository.findAll();
    }

    public Iterable<FuelType> getFuelType() {
        return fuelTypeRepository.findAll();
    }
}
