package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.Mapper.RepairMapper;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepairRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;


@Service
public class RepairService {
    VehicleRepairRepository vehicleRepairRepository;
    VehicleRepository vehicleRepository;
    UserRepository userRepository;
    RepairMapper mapper;
    @Autowired
    public RepairService(VehicleRepairRepository vehicleRepairRepository, VehicleRepository vehicleRepository,
                         UserRepository userRepository, RepairMapper mapper) {
        this.vehicleRepairRepository = vehicleRepairRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void save(RepairDTO repairDTO){
        VehicleRepair vehicleRepair = mapper.repairDTOtoVehicleRepair(repairDTO);
        vehicleRepair.setVehicle(vehicleRepository.findById(repairDTO.getVehicleId()).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania")));
        vehicleRepairRepository.save(vehicleRepair);
    }
    /*
    get by vehicle
     */
    public Iterable<RepairDTO> getRepairsByVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        return mapper.vehicleRepairsToRepairsDTO(vehicleRepairRepository.findAllByVehicle(vehicle));
    }
    /*
    get by vehicle owner
    for superuser
     */
    public Iterable<RepairDTO> getRepairsByUser(String email){
        User user=userRepository.findUserByEmail(email);
        Iterable<Vehicle> vehicles = vehicleRepository.findVehiclesByUser(user);
        return mapper.vehicleRepairsToRepairsDTO(vehicleRepairRepository.findAllByVehicleIn(vehicles));
    }
    /*
    get by repair id
     */
    public RepairDTO getRepairById(Long id) {
        return mapper.vehicleRepairToRepairDTO(vehicleRepairRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania")));
    }
    public void deleteRepairById(Long id){
        vehicleRepairRepository.deleteById(id);
    }
}
