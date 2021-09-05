package pl.nankiewic.fleetappbackend.Service;

import lombok.AllArgsConstructor;
import pl.nankiewic.fleetappbackend.Entity.*;
import pl.nankiewic.fleetappbackend.Repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class CheckService {

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
        return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
    }

    public boolean accessToInspection(String email, Long inspectionId) {
        User user = userRepository.findUserByEmail(email);
        VehicleInspection vehicleInspection = vehicleInspectionRepository.findById(inspectionId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: inspekcja"));
        Vehicle vehicle = vehicleInspection.getVehicle();
        return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
    }

    public boolean accessToRepair(String email, Long repairId) {
        User user = userRepository.findUserByEmail(email);
        VehicleRepair vehicleRepair = vehicleRepairRepository.findById(repairId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: naprawa"));
        Vehicle vehicle = vehicleRepair.getVehicle();
        return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
    }

    public boolean accessToInsurance(String email, Long insuranceId) {
        User user = userRepository.findUserByEmail(email);
        VehicleInsurance vehicleInsurance = vehicleInsuranceRepository.findById(insuranceId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: ubezpieczenie"));
        Vehicle vehicle = vehicleInsurance.getVehicle();
        return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
    }

    public boolean accessToRefueling(String email, Long refuelingId) {
        User user = userRepository.findUserByEmail(email);
        VehicleRefueling refueling = vehicleRefuelingRepository.findById(refuelingId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: tankowanie"));
        Vehicle vehicle = refueling.getVehicle();
        return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
    }

    public boolean accessToUse(String email, Long useId) {
        User user = userRepository.findUserByEmail(email);
        VehicleUse vehicleUse = vehicleUseRepository.findById(useId).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono zasobu: użycie"));
        Vehicle vehicle = vehicleUse.getVehicle();
        return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
    }

    private boolean isMyVehicle(User user, Vehicle vehicle) {
        return vehicle.getUser().getId().equals(user.getId());
    }

    private boolean isMyVehicleNow(User user, Vehicle vehicle) {
        Iterable<CurrentVehicleUser> users = currentVehicleUserRepository.findCurrentVehicleUsersByVehicleIs(vehicle);
        for (CurrentVehicleUser x : users) {
            return x.getUser().equals(user);
        }
        return false;
    }

}
