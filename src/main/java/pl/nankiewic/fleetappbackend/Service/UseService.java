package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;
import pl.nankiewic.fleetappbackend.Mapper.UseMapper;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleUseRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class UseService {
    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final UseMapper useMapper;

    @Autowired
    public UseService(VehicleUseRepository vehicleUseRepository,
                      VehicleRepository vehicleRepository,
                      UserRepository userRepository,
                      UseMapper useMapper) {
        this.vehicleUseRepository = vehicleUseRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.useMapper = useMapper;
    }

    public void save(UseDTO use, String email) {
        VehicleUse vehicleUse = useMapper.useDTOtoVehicleUse(use);
        Vehicle vehicle = vehicleRepository.findById(use.getVehicleId()).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        vehicleUse.setVehicle(vehicle);
        vehicleUse.setUser(userRepository.findUserByEmail(email));

        int mil = Integer.parseInt(vehicle.getMileage()) + use.getTrip();
        vehicle.setMileage(Integer.toString(mil));
        vehicleUseRepository.save(vehicleUse);
        vehicleRepository.save(vehicle);
    }

    public Iterable<UseDTO> getUseByVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            return useMapper.vehicleUseToUseDTO(vehicleUseRepository.findAllByVehicle(
                    vehicleRepository.findById(id).orElseThrow(
                            () -> new RuntimeException("Bład przetwarzania"))));
        } else throw new EntityNotFoundException();
    }

    public Iterable<UseDTO> getUseByUser(String email) {
        return useMapper.vehicleUseToUseDTO(vehicleUseRepository.findAllByUser(userRepository.findUserByEmail(email)));
    }

    public UseDTO getUseById(Long id) {
        if (vehicleUseRepository.existsById(id)) {
            return useMapper.vehicleUseToUseDTO(vehicleUseRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Bład przetwarzania")));
        } else throw new EntityNotFoundException();
    }

    public void deleteUseById(Long id) {
        VehicleUse use = vehicleUseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        Vehicle vehicle = use.getVehicle();
        int mil = Integer.parseInt(vehicle.getMileage()) - use.getTrip();
        vehicle.setMileage(Integer.toString(mil));
        vehicleRepository.save(vehicle);
        vehicleUseRepository.deleteById(id);
    }

    public Iterable<UseDTO> getUseByUserAndVehicle(Long userId, Long vehicleId) {
        if (userRepository.existsById(userId) && vehicleRepository.existsById(vehicleId)) {
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                    () -> new RuntimeException("Bład przetwarzania"));
            User user = userRepository.findUserById(userId);
            return useMapper.vehicleUseToUseDTO(vehicleUseRepository.findAllByVehicleAndUser(vehicle, user));
        } else throw new EntityNotFoundException("Nie znaleziono zasobu pojazd lub użytkownik");
    }
}
