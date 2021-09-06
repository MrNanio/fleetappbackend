package pl.nankiewic.fleetappbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;
import pl.nankiewic.fleetappbackend.Mapper.UseMapper;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleUseRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class UseService {

    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final UseMapper useMapper;

    public void createVehicleUse(UseDTO use, String email) {

        VehicleUse vehicleUse = useMapper.vehicleUseDtoToEntity(use);
        addMileageToVehicle(use.getVehicleId(), use.getTrip());
        vehicleUse.setUser(userRepository.findUserByEmail(email));
        vehicleUseRepository.save(vehicleUse);
    }

    public void updateVehicleUse(UseDTO useDTO) {

        VehicleUse vehicleUse = vehicleUseRepository.findById(useDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Bład przetwarzania"));
        Short currentTripMileageFromDatabase = vehicleUse.getTrip();
        Short newValueOfMilTrip = useDTO.getTrip();
        useMapper.updateVehicleUseFromDto(vehicleUse, useDTO);
        recalculateMileageToVehicle(useDTO.getVehicleId(), currentTripMileageFromDatabase, newValueOfMilTrip);
        vehicleUseRepository.save(vehicleUse);
    }

    public Iterable<UseDTO> getUseByVehicle(Long id) {

        if (vehicleRepository.existsById(id)) {
            return vehicleUseRepository.findAllByVehicleId(id);
        } else throw new EntityNotFoundException();
    }

    public UseDTO getUseByUseId(Long id) {
        return vehicleUseRepository.findByUseId(id);
    }

    public Iterable<UseDTO> getUseByUser(String email) {
        return vehicleUseRepository.findAllByUserId(userRepository.findUserByEmail(email).getId());
    }

    public Iterable<UseDTO> getUseByUserAndVehicle(Long userId, Long vehicleId) {
        if (userRepository.existsById(userId) && vehicleRepository.existsById(vehicleId)) {
            return vehicleUseRepository.findAllByVehicleAndUser(vehicleId, userId);
        } else throw new EntityNotFoundException("Nie znaleziono zasobu pojazd lub użytkownik");
    }

    public void deleteUseById(Long id) {
        VehicleUse use = vehicleUseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Bład przetwarzania"));
        Vehicle vehicle = use.getVehicle();
        int mil = Integer.parseInt(vehicle.getMileage()) - use.getTrip();
        vehicle.setMileage(Integer.toString(mil));
        vehicleRepository.save(vehicle);
        vehicleUseRepository.deleteById(id);
    }

    private void addMileageToVehicle(Long vehicleId, Short trip) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new EntityNotFoundException("Bład przetwarzania"));
        int mil = Integer.parseInt(vehicle.getMileage()) + trip;
        vehicle.setMileage(Integer.toString(mil));
        vehicleRepository.save(vehicle);

    }

    private void recalculateMileageToVehicle(Long vehicleId, Short tripValueFromDatabase, Short updateValueOfTrip) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new EntityNotFoundException("Bład przetwarzania"));
        int mil = Integer.parseInt(vehicle.getMileage()) - tripValueFromDatabase + updateValueOfTrip;
        vehicle.setMileage(Integer.toString(mil));
        vehicleRepository.save(vehicle);
    }

}
