package pl.nankiewic.fleetappbackend.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.Entity.*;
import pl.nankiewic.fleetappbackend.Repository.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
public class ReportsService {
    UserRepository userRepository;
    VehicleRepository vehicleRepository;
    RefuelingRepository refuelingRepository;
    VehicleUseRepository vehicleUseRepository;
    VehicleRepairRepository vehicleRepairRepository;
    VehicleInsuranceRepository vehicleInsuranceRepository;
    VehicleInspectionRepository vehicleInspectionRepository;
    @Autowired
    public ReportsService(UserRepository userRepository, VehicleRepository vehicleRepository,
                          RefuelingRepository refuelingRepository, VehicleUseRepository vehicleUseRepository,
                          VehicleRepairRepository vehicleRepairRepository,
                          VehicleInsuranceRepository vehicleInsuranceRepository,
                          VehicleInspectionRepository vehicleInspectionRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.refuelingRepository = refuelingRepository;
        this.vehicleUseRepository = vehicleUseRepository;
        this.vehicleRepairRepository = vehicleRepairRepository;
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
        this.vehicleInspectionRepository = vehicleInspectionRepository;
    }

    //get report by id vehicle and refueling date
    public Iterable<Refueling> refuelingByVehicle(Long id, LocalDate begin, LocalDate end){
       if(vehicleRepository.existsById(id)){
           Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
           return refuelingRepository.findAllByVehicleIsAndRefuelingDateIsBetween(vehicle, begin, end);
       } else throw new EntityNotFoundException("xd");
    }
    //get report by id vehicle and insurance date
    public Iterable<VehicleInsurance> insuranceByVehicle(Long id, LocalDate begin, LocalDate end){
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
            return vehicleInsuranceRepository.findAllByVehicleAndEffectiveDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("xd");
    }
    //get report by id vehicle and inspection date
    public Iterable<VehicleInspection> inspectionByVehicle(Long id, LocalDate begin, LocalDate end){
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
            return vehicleInspectionRepository.findAllByVehicleAndInspectionDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("xd");
    }
    //get report by id vehicle and repair date
    public Iterable<VehicleRepair> repairByVehicle(Long id, LocalDate begin, LocalDate end){
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
            return vehicleRepairRepository.findAllByVehicleAndRepairDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("xd");
    }
    //get report by id vehicle and use date
    public Iterable<VehicleUse> useByVehicle(Long id, LocalDate begin, LocalDate end){
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
            return vehicleUseRepository.findAllByVehicleIsAndTripDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("xd");
    }
    //get report by id user and use date
    public Iterable<VehicleUse> useByUser(Long id, LocalDate begin, LocalDate end) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findUserById(id);
            return vehicleUseRepository.findAllByUserIsAndTripDateBetween(user, begin, end);
        } else throw new EntityNotFoundException("xd");
    }
    //get vehicle list by user
    public Iterable<Vehicle> vehicleByUser(String email){
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findUserByEmail(email);
            return vehicleRepository.findVehiclesByUser(user);
        } else throw new EntityNotFoundException("xd");
    }
   //get info about vehicle
    public Vehicle getVehicleInfo(Long id){
        return vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
    }
}
