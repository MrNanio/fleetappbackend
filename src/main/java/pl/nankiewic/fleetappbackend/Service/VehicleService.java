package pl.nankiewic.fleetappbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.Entity.FuelType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.*;
import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMakeRepository vehicleMakeRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;

    public void createVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO, String email) {
        Vehicle vehicle = vehicleMapper.vehicleDTOtoVehicle(vehicleRequestResponseDTO);
        vehicle.setUser(userRepository.findUserByEmail(email));
        vehicleRepository.save(vehicle);
    }

    public void updateVehicle(VehicleRequestResponseDTO vehicleRequestResponseDTO) {
        Vehicle vehicle = vehicleRepository.findById(vehicleRequestResponseDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: pojazd"));
        vehicleMapper.updateVehicleFromRequest(vehicle, vehicleRequestResponseDTO);
        vehicleRepository.save(vehicle);
    }

    public Iterable<VehicleDTO> getVehiclesDataByUser(String email) {
        if (vehicleRepository.existsByUser(userRepository.findUserByEmail(email))) {
            if (userRepository.existsByEmail(email)) {
                return vehicleRepository.findVehiclesDataByUser(userRepository.findUserByEmail(email));
            } else throw new EntityNotFoundException("Nie znaleziono użytkownika");
        } else throw new EntityNotFoundException("Nie znaleziono pojazdów użytkownika");
    }

    public VehicleDTO getVehicleDataById(Long id) {
        if (vehicleRepository.existsById(id)) {
            return vehicleRepository.findVehicleDetailsById(id);
        } else throw new EntityNotFoundException("Nie znaleziono pojazdu o danym id");
    }

    public void deleteVehicleById(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Iterable<VehicleMake> getMake() {
        return vehicleMakeRepository.findAll();
    }

    public Iterable<FuelType> getFuelType() {
        return fuelTypeRepository.findAll();
    }
}
