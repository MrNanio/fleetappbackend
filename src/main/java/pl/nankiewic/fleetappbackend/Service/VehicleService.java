package pl.nankiewic.fleetappbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.Entity.FuelType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.FuelTypeRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleMakeRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMakeRepository vehicleMakeRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public void createVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO, String email) {
        Vehicle vehicle = vehicleMapper.vehicleDTOtoVehicle(vehicleRequestResponseDTO);
        vehicle.setUser(userRepository.findUserByEmail(email));
        vehicleRepository.save(vehicle);
    }

    public void updateVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, vehicleRequestResponseDTO.getId())) {
            Vehicle vehicle = vehicleRepository.findById(vehicleRequestResponseDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Nie znaleziono zasobu: pojazd"));
            vehicleMapper.updateVehicleFromRequest(vehicle, vehicleRequestResponseDTO);
            vehicleRepository.save(vehicle);
        } else throw new PermissionDeniedException();
    }

    public Iterable<VehicleDTO> getVehiclesDataByUser(String email) {
        if (vehicleRepository.existsByUser(userRepository.findUserByEmail(email))) {
            if (userRepository.existsByEmail(email)) {
                return vehicleRepository.findVehiclesDataByUser(email);
            } else throw new EntityNotFoundException("Nie znaleziono użytkownika");
        } else throw new EntityNotFoundException("Nie znaleziono pojazdów użytkownika");
    }

    public VehicleView getVehicleDataById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, id)) {
            return vehicleRepository.findVehicleDetailsById(id);
        } else throw new PermissionDeniedException();
    }

    public void deleteVehicleById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, id)) {
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
