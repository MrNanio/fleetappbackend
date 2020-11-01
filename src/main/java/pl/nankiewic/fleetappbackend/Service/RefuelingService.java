package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.Entity.Refueling;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Mapper.RefuelingMapper;
import pl.nankiewic.fleetappbackend.Repository.RefuelingRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import java.util.Optional;


@Service
public class RefuelingService {

    VehicleRepository vehicleRepository;
    RefuelingRepository refuelingRepository;
    UserRepository userRepository;
    RefuelingMapper mapper;
    @Autowired
    public RefuelingService(VehicleRepository vehicleRepository, RefuelingRepository refuelingRepository,
                            UserRepository userRepository, RefuelingMapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.refuelingRepository = refuelingRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /*
        add refueling
         */
    public void save(RefuelingDTO refuelingDTO, String email) {
        Refueling refueling = mapper.refuelingDTOToRefueling(refuelingDTO);
        refueling.setVehicle(vehicleRepository.findById(refuelingDTO.getVehicleId()).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania")));
        refueling.setUser(userRepository.findUserByEmail(email));
        refuelingRepository.save(refueling);
    }
    /*
    get by refueling id
     */
    public RefuelingDTO getRefuelingById(Long id) {
        return mapper.refuelingToRefuelingDTO(refuelingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania")));
    }
    /*
    get by vehicle
     */
    public Iterable <RefuelingDTO> getRefuelingByVehicle(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            return mapper.refuelingToRefuelingDTO(refuelingRepository.findRefuelingsByVehicle(vehicle.get()));
        }
        throw new RuntimeException("Bład przetwarzania");
    }
    /*
    get by vehicle owner
    for superuser
     */
    public Iterable <RefuelingDTO> getRefuelingByUser(String email){
        User user=userRepository.findUserByEmail(email);
        Iterable<Vehicle> vehicles = vehicleRepository.findVehiclesByUser(user);
        return mapper.refuelingToRefuelingDTO(refuelingRepository.findRefuelingsByVehicleIn(vehicles));
    }
    /*
   get by author of item
   for user
    */
    public Iterable <RefuelingDTO> getRefuelingByAuthor(String email){
        User user=userRepository.findUserByEmail(email);
        return mapper.refuelingToRefuelingDTO(refuelingRepository.findRefuelingsByUser(user));
    }

    public void deleteRefuelingById(Long id) {
        refuelingRepository.deleteById(id);
    }
}
