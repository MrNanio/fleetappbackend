package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.dto.UseDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleUse;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.UseMapper;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleUseRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
public class UseService {

    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final UseMapper useMapper;

    public void createVehicleUse(UseDTO use, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, use.getVehicleId())) {
            VehicleUse vehicleUse = useMapper.vehicleUseDtoToEntity(use);
            addMileageToVehicle(use.getVehicleId(), use.getTrip());
            vehicleUse.setUser(userRepository.findUserByEmail(email).orElseThrow());
            vehicleUseRepository.save(vehicleUse);
        } else throw new PermissionDeniedException();
    }

    public void updateVehicleUse(UseDTO useDTO, String email) {
        if (checkExistAndPermissionComponent.accessToUse(email, useDTO.getId())) {
            VehicleUse vehicleUse = vehicleUseRepository.findById(useDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Use not found"));
            Short currentTripMileageFromDatabase = vehicleUse.getTrip();
            Short newValueOfMilTrip = useDTO.getTrip();
            useMapper.updateVehicleUseFromDto(vehicleUse, useDTO);
            recalculateMileageToVehicle(useDTO.getVehicleId(), currentTripMileageFromDatabase, newValueOfMilTrip);
            vehicleUseRepository.save(vehicleUse);
        } else throw new PermissionDeniedException();
    }

    public List<UseDTO> getUseByVehicle(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, id)) {
            return vehicleUseRepository.findAllByVehicleId(id);
        } else throw new PermissionDeniedException();
    }

    public UseDTO getUseByUseId(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToUse(email, id)) {
            return vehicleUseRepository.findByUseId(id);
        } else throw new PermissionDeniedException();
    }

    public List<UseDTO> getUseByUser(String email) {
        return vehicleUseRepository.findAllByUserId(userRepository.findUserByEmail(email).orElseThrow().getId());
    }

    public List<UseDTO> getUseByUserAndVehicle(Long userId, Long vehicleId, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, vehicleId)) {
            return vehicleUseRepository.findAllByVehicleAndUser(vehicleId, userId);
        } else throw new PermissionDeniedException();
    }

    public void deleteUseById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToUse(email, id)) {
            VehicleUse use = vehicleUseRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Bład przetwarzania"));
            Vehicle vehicle = use.getVehicle();
            int mil = Integer.parseInt(vehicle.getMileage()) - use.getTrip();
            vehicle.setMileage(Integer.toString(mil));
            vehicleRepository.save(vehicle);
            vehicleUseRepository.deleteById(id);
        } else throw new PermissionDeniedException();
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
