package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.Entity.*;
import pl.nankiewic.fleetappbackend.Repository.*;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;

@Service
public class ReportsService {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepairRepository vehicleRepairRepository;
    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final VehicleRefuelingRepository vehicleRefuelingRepository;
    @Autowired
    public ReportsService(UserRepository userRepository, VehicleRepository vehicleRepository,
                          VehicleUseRepository vehicleUseRepository, VehicleRepairRepository vehicleRepairRepository,
                          VehicleInsuranceRepository vehicleInsuranceRepository,
                          VehicleInspectionRepository vehicleInspectionRepository,
                          VehicleRefuelingRepository vehicleRefuelingRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleUseRepository = vehicleUseRepository;
        this.vehicleRepairRepository = vehicleRepairRepository;
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
        this.vehicleInspectionRepository = vehicleInspectionRepository;
        this.vehicleRefuelingRepository = vehicleRefuelingRepository;
    }

    //get report by id vehicle and refueling date
    public Iterable<VehicleRefueling> refuelingByVehicle(Long id, Date begin, Date end){
       if(vehicleRepository.existsById(id)){
           Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
           return vehicleRefuelingRepository.findAllByVehicleIsAndRefuelingDateIsBetween(vehicle, begin, end);
       } else throw new EntityNotFoundException("not found");
    }
    //get report by id vehicle and insurance date
    public Iterable<VehicleInsurance> insuranceByVehicle(Long id, Date begin, Date end){
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
            return vehicleInsuranceRepository.findAllByVehicleAndEffectiveDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }
    //get report by id vehicle and inspection date
    public Iterable<VehicleInspection> inspectionByVehicle(Long id, Date begin, Date end){
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
            return vehicleInspectionRepository.findAllByVehicleAndInspectionDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }
    //get report by id vehicle and repair date
    public Iterable<VehicleRepair> repairByVehicle(Long id, Date begin, Date end){
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
            return vehicleRepairRepository.findAllByVehicleAndRepairDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }
    //get report by id vehicle and use date
    public Iterable<VehicleUse> useByVehicle(Long id, Date begin, Date end){
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
            return vehicleUseRepository.findAllByVehicleIsAndTripDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }
    //get report by id user and use date
    public Iterable<VehicleUse> useByUser(Long id, Date begin, Date end) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findUserById(id);
            return vehicleUseRepository.findAllByUserIsAndTripDateBetween(user, begin, end);
        } else throw new EntityNotFoundException("not found");
    }
    //get report by id user and refueling date
    public Iterable<VehicleRefueling> refuelingByUser(Long id, Date begin, Date end) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findUserById(id);
            return vehicleRefuelingRepository.findAllByUserIsAndRefuelingDateIsBetween(user, begin, end);
        } else throw new EntityNotFoundException("not found");
    }

    //get vehicle list by user
    public Iterable<Vehicle> vehicleByUser(String email){
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findUserByEmail(email);
            return vehicleRepository.findVehiclesByUser(user);
        } else throw new EntityNotFoundException("not found");
    }
   //get info about vehicle
    public Vehicle getVehicleInfo(Long id){
        return vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"));
    }





}
