package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShareService {
    VehicleRepository vehicleRepository;
    UserRepository userRepository;
    CurrentVehicleUserRepository currentVehicleUserRepository;
    VehicleMapper vehicleMapper;
    @Autowired
    public ShareService(VehicleRepository vehicleRepository, UserRepository userRepository,
                        CurrentVehicleUserRepository currentVehicleUserRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.currentVehicleUserRepository = currentVehicleUserRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public void setCurrentVehicleUserToVehicle(ShareDTO shareDTO, String email) {
       if(userRepository.existsByEmail(email) && userRepository.existsById(shareDTO.getUserId())){
           User user = userRepository.findUserById(shareDTO.getUserId());
           for (String vId : shareDTO.getVehicleId()) {
               if(vehicleRepository.existsById(Long.valueOf(vId))){
                   Vehicle vehicle = vehicleRepository.findById(Long.valueOf(vId)).orElseThrow(
                           () -> new RuntimeException("Bład przetwarzania"));
                   CurrentVehicleUser currentVehicleUser = new CurrentVehicleUser(user, vehicle);
                   currentVehicleUserRepository.save(currentVehicleUser);
               } else throw new EntityNotFoundException("nie znaleziono zasobu: pojazd");
           }
       } else throw new EntityNotFoundException("nie znaleziono zasobu: użytkownik");
    }

    public Iterable<VehicleDTO> getShareVehicleListByUserId(Long id, String username) {
        if(userRepository.existsByEmail(username) && userRepository.existsById(id)){
            User manager =userRepository.findUserByEmail(username);
            User user = userRepository.findUserById(id);
            List<Vehicle> myShare = new ArrayList<Vehicle>();
            Iterable<CurrentVehicleUser> myByShare = currentVehicleUserRepository.findCurrentVehicleUsersByUserIs(user);
            for (CurrentVehicleUser currentVehicleUser : myByShare) {
                myShare.add(currentVehicleUser.getVehicle());
            }
            return vehicleMapper.map(myShare);
        } else throw new EntityNotFoundException("błąd zasobu: użytkownik");
    }

    public Iterable<VehicleDTO> getPossibleVehiclesList(String username) {
        if(userRepository.existsByEmail(username)){
            User user =userRepository.findUserByEmail(username);
            List<Vehicle> myAll = new ArrayList<Vehicle>();
            Iterable<Vehicle> myByOwn =vehicleRepository.findVehiclesByUser(user);
            for (Vehicle vehicle : myByOwn) {
                if(!currentVehicleUserRepository.existsByVehicle(vehicle)){
                    myAll.add(vehicle);
                }
            }
            return vehicleMapper.map(myAll);
        } else throw new EntityNotFoundException("błąd zasobu: użytkownik");
    }

    public void deleteShareVehicleListByVehicleId(Long id, String username) {
        if(vehicleRepository.existsById(id)){
            Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Bład przetwarzania"));
            if(currentVehicleUserRepository.existsByVehicle(vehicle)){
                CurrentVehicleUser currentVehicleUser=currentVehicleUserRepository.findByVehicle(vehicle);
                currentVehicleUserRepository.deleteById(currentVehicleUser.getId());
            }else throw new EntityNotFoundException("błąd zasobu: share");
        }else throw new EntityNotFoundException("błąd zasobu: pojazd");
    }
}
