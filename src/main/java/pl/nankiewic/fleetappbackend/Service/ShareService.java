package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.Repository.CurrentVehicleUserRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

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

   /* public void setCurrentVehicleUserToVehicle(ShareDTO shareDTO) {
        Vehicle vehicle = vehicleRepository.findById(shareDTO.getVehicleId()).get();
        User user = userRepository.findUserById(shareDTO.getUserId());
        CurrentVehicleUser currentVehicleUser = new CurrentVehicleUser(user, vehicle);
        currentVehicleUserRepository.save(currentVehicleUser);
    }
    public ShareVehicleRespond shareOption(Long id, String email){
        Vehicle vehicle=vehicleRepository.findById(id).get();
        Iterable<User> users=userRepository.findByUser(userRepository.findUserByEmail(email));
        return new ShareVehicleRespond(vehicle.getId(), users);
    }*/
}
