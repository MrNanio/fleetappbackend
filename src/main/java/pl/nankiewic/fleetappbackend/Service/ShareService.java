package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;
import pl.nankiewic.fleetappbackend.Entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class ShareService {
    VehicleRepository vehicleRepository;
    UserRepository userRepository;
    CurrentVehicleUserRepository currentVehicleUserRepository;
    @Autowired
    public ShareService(VehicleRepository vehicleRepository, UserRepository userRepository, CurrentVehicleUserRepository currentVehicleUserRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.currentVehicleUserRepository = currentVehicleUserRepository;
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

}
