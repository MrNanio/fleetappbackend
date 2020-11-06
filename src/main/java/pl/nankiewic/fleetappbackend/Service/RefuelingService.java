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

import javax.persistence.EntityNotFoundException;
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
        Vehicle vehicle=vehicleRepository.findById(refuelingDTO.getVehicleId()).orElseThrow(  () -> new RuntimeException("Bład przetwarzania"));
        refueling.setVehicle(vehicle);
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
    public Iterable<RefuelingDTO> getRefuelingByUserAndVehicle(Long userId, Long vehicleId) {
        if (userRepository.existsById(userId) && vehicleRepository.existsById(vehicleId)) {
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                    () -> new RuntimeException("Bład przetwarzania"));
            User user = userRepository.findUserById(userId);
            return mapper.refuelingToRefuelingDTO(refuelingRepository.findAllByVehicleAndUser(vehicle, user));
        } else throw new EntityNotFoundException("Nie znaleziono zasobu pojazd lub użytkownik");
    }
}
