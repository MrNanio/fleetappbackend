package pl.nankiewic.fleetappbackend.Service;
import pl.nankiewic.fleetappbackend.Entity.*;
import pl.nankiewic.fleetappbackend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
    @Autowired
    public CheckService(CurrentVehicleUserRepository currentVehicleUserRepository,
                        VehicleInspectionRepository vehicleInspectionRepository,
                        VehicleRefuelingRepository vehicleRefuelingRepository,
                        VehicleInsuranceRepository vehicleInsuranceRepository,
                        VehicleRepairRepository vehicleRepairRepository,
                        VehicleUseRepository vehicleUseRepository, VehicleRepository vehicleRepository,
                        UserRepository userRepository) {
        this.currentVehicleUserRepository = currentVehicleUserRepository;
        this.vehicleInspectionRepository = vehicleInspectionRepository;
        this.vehicleRefuelingRepository = vehicleRefuelingRepository;
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
        this.vehicleRepairRepository = vehicleRepairRepository;
        this.vehicleUseRepository = vehicleUseRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public boolean isMyVehicle(User user, Vehicle vehicle){
        return vehicle.getUser().getId().equals(user.getId());
    }
    public boolean isMyVehicleNow(User user, Vehicle vehicle){
        Iterable <CurrentVehicleUser> users=currentVehicleUserRepository.findCurrentVehicleUsersByVehicleIs(vehicle);
        for (CurrentVehicleUser x : users) {
            return x.getUser() == user;
        }
        return false;
    }
    public boolean accessToVehicle(String email, Long vehicleId){
        User user=userRepository.findUserByEmail(email);
        if(vehicleRepository.existsById(vehicleId)) {
            Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
            if (vehicle.isPresent()) {
                Vehicle vehicle1 = vehicle.get();
                return isMyVehicle(user, vehicle1) || isMyVehicleNow(user, vehicle1);
            } else throw new EntityNotFoundException("Zasób nie istnieje: pojazd");
        } else throw new EntityNotFoundException("Zasób nie istnieje: pojazd");
    }
    public boolean accessToInspection(String email, Long inspectionId){
        User user=userRepository.findUserByEmail(email);
        if(vehicleInspectionRepository.existsById(inspectionId)) {
            Optional<VehicleInspection> vehicleInspection = vehicleInspectionRepository.findById(inspectionId);
            if (vehicleInspection.isPresent()) {
                Vehicle vehicle = vehicleInspection.get().getVehicle();
                return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
            } else throw new EntityNotFoundException("Zasób nie istnieje: inspekcja");
        } else throw new EntityNotFoundException("Zasób nie istnieje: inspekcja");
    }
    public boolean accessToRepair(String email, Long repairId){
        User user=userRepository.findUserByEmail(email);
        if(vehicleRepairRepository.existsById(repairId)) {
            Optional<VehicleRepair> vehicleRepair = vehicleRepairRepository.findById(repairId);
            if (vehicleRepair.isPresent()) {
                Vehicle vehicle = vehicleRepair.get().getVehicle();
                return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
            } else throw new EntityNotFoundException("Zasób nie istnieje: naprawa");
        } else throw new EntityNotFoundException("Zasób nie istnieje: naprawa");
    }
    public boolean accessToInsurance(String email, Long insuranceId){
        User user=userRepository.findUserByEmail(email);
        if(vehicleInsuranceRepository.existsById(insuranceId)) {
            Optional<VehicleInsurance> vehicleInsurance = vehicleInsuranceRepository.findById(insuranceId);
            if (vehicleInsurance.isPresent()) {
                Vehicle vehicle = vehicleInsurance.get().getVehicle();
                return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
            } else throw new EntityNotFoundException("Zasób nie istnieje: ubezpiecznie");
        } else throw new EntityNotFoundException("Zasób nie istnieje: ubezpiecznie");
    }
    public boolean accessToRefueling(String email, Long refuelingId){
        User user=userRepository.findUserByEmail(email);
        if(vehicleRefuelingRepository.existsById(refuelingId)) {
            Optional<VehicleRefueling> refueling = vehicleRefuelingRepository.findById(refuelingId);
            if (refueling.isPresent()) {
                Vehicle vehicle = refueling.get().getVehicle();
                return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
            } else throw new EntityNotFoundException("Zasób nie istnieje: tankowanie");
        } else throw new EntityNotFoundException("Zasób nie istnieje: tankowanie");
    }
    public boolean accessToUse(String email, Long useId){
        User user=userRepository.findUserByEmail(email);
        if(vehicleUseRepository.existsById(useId)){
            Optional<VehicleUse> vehicleUse = vehicleUseRepository.findById(useId);
            if (vehicleUse.isPresent()) {
                Vehicle vehicle = vehicleUse.get().getVehicle();
                return isMyVehicle(user, vehicle) || isMyVehicleNow(user, vehicle);
            } else throw new EntityNotFoundException("Zasób nie istnieje: użycie");
        } else throw new EntityNotFoundException("Zasób nie istnieje: użycie");
    }
    /*
    public boolean accessToUser(String email, Long userId){
        User user=userRepository.findUserByEmail(email);
        if(userRepository.existsById(userId)){
            User user1=userRepository.findUserById(userId);
            return (user1.getUser() == null && user == user1) || user1.getUser() == user;
        } else throw new EntityNotFoundException("Zasób nie istnieje: user");
    }*/
}
