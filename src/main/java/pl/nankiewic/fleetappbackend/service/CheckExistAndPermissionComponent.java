package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.nankiewic.fleetappbackend.entity.*;
import pl.nankiewic.fleetappbackend.repository.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Component
public class CheckExistAndPermissionComponent {

    private final CurrentVehicleUserRepository currentVehicleUserRepository;
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final VehicleRefuelingRepository vehicleRefuelingRepository;
    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final VehicleRepairRepository vehicleRepairRepository;
    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public boolean accessToVehicle(String email, Long vehicleId) {
        User user = userRepository.findUserByEmail(email);
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: pojazd"));
        return isMyVehicleByOwn(user, vehicle) || isMyVehicleByShare(user, vehicle);
    }

    public boolean accessToInspection(String email, Long inspectionId) {
        User user = userRepository.findUserByEmail(email);
        VehicleInspection vehicleInspection = vehicleInspectionRepository.findById(inspectionId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: inspekcja"));
        Vehicle vehicle = vehicleInspection.getVehicle();
        return isMyVehicleByOwn(user, vehicle) || isMyVehicleByShare(user, vehicle);
    }

    public boolean accessToRepair(String email, Long repairId) {
        User user = userRepository.findUserByEmail(email);
        VehicleRepair vehicleRepair = vehicleRepairRepository.findById(repairId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: naprawa"));
        Vehicle vehicle = vehicleRepair.getVehicle();
        return isMyVehicleByOwn(user, vehicle) || isMyVehicleByShare(user, vehicle);
    }

    public boolean accessToInsurance(String email, Long insuranceId) {
        User user = userRepository.findUserByEmail(email);
        VehicleInsurance vehicleInsurance = vehicleInsuranceRepository.findById(insuranceId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: ubezpieczenie"));
        Vehicle vehicle = vehicleInsurance.getVehicle();
        return isMyVehicleByOwn(user, vehicle) || isMyVehicleByShare(user, vehicle);
    }

    public boolean accessToRefueling(String email, Long refuelingId) {
        User user = userRepository.findUserByEmail(email);
        VehicleRefueling refueling = vehicleRefuelingRepository.findById(refuelingId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: tankowanie"));
        Vehicle vehicle = refueling.getVehicle();
        return isMyVehicleByOwn(user, vehicle) || isMyVehicleByShare(user, vehicle);
    }

    public boolean accessToUse(String email, Long useId) {
        User user = userRepository.findUserByEmail(email);
        VehicleUse vehicleUse = vehicleUseRepository.findById(useId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: użycie"));
        Vehicle vehicle = vehicleUse.getVehicle();
        return isMyVehicleByOwn(user, vehicle) || isMyVehicleByShare(user, vehicle);
    }

    private boolean isMyVehicleByOwn(User user, Vehicle vehicle) {
        return vehicle.getUser().getId().equals(user.getId());
    }

    private boolean isMyVehicleByShare(User user, Vehicle vehicle) {
        List<CurrentVehicleUser> currentVehicleUsers = currentVehicleUserRepository.findCurrentVehicleUsersByVehicleIs(vehicle);
        return currentVehicleUsers.stream().anyMatch(u -> u.getUser().equals(user));

    }

}
