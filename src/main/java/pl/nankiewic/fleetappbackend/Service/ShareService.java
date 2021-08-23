package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.ShareDTO;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
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
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final CurrentVehicleUserRepository currentVehicleUserRepository;
    private final VehicleMapper vehicleMapper;

    @Autowired
    public ShareService(VehicleRepository vehicleRepository, UserRepository userRepository,
                        CurrentVehicleUserRepository currentVehicleUserRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.currentVehicleUserRepository = currentVehicleUserRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public void setCurrentVehicleUserToVehicle(ShareDTO shareDTO, String email) {
        if (userRepository.existsByEmail(email) && userRepository.existsById(shareDTO.getUserId())) {
            User user = userRepository.findById(shareDTO.getUserId()).orElseThrow(
                    () -> new EntityNotFoundException("Nie znaleziono użytkownika"));
            for (String vId : shareDTO.getVehicleId()) {
                if (vehicleRepository.existsById(Long.valueOf(vId))) {
                    Vehicle vehicle = vehicleRepository.findById(Long.valueOf(vId)).orElseThrow(
                            () -> new RuntimeException("Bład przetwarzania"));
                    CurrentVehicleUser currentVehicleUser = new CurrentVehicleUser(user, vehicle);
                    currentVehicleUserRepository.save(currentVehicleUser);
                } else throw new EntityNotFoundException("nie znaleziono zasobu: pojazd");
            }
        } else throw new EntityNotFoundException("nie znaleziono zasobu: użytkownik");
    }

//    public Iterable<VehicleRequestResponseDTO> getShareVehicleListByUserId(Long id, String username) {
//        if (userRepository.existsByEmail(username) && userRepository.existsById(id)) {
//            //User manager =userRepository.findUserByEmail(username);
//            User user = userRepository.findById(id).orElseThrow(
//                    () -> new EntityNotFoundException("Nie znaleziono użytkownika"));
//            List<CurrentVehicleUser> myByShare = currentVehicleUserRepository.findCurrentVehicleUsersByUserIs(user);
//            List<VehicleDTO> myShare = myByShare.stream().map(CurrentVehicleUser::getVehicle).collect(Collectors.toList());
//            return vehicleMapper.map(myShare);
//        } else throw new EntityNotFoundException("błąd zasobu: użytkownik");
//    }

    public Iterable<VehicleRequestResponseDTO> getPossibleVehiclesList(String username) {
        if (userRepository.existsByEmail(username)) {
            User user = userRepository.findUserByEmail(username);
            List<VehicleDTO> myAll = new ArrayList<>();
            Iterable<VehicleDTO> myByOwn = vehicleRepository.selectVehiclesDataByUser(user);
            for (VehicleDTO vehicle : myByOwn) {
                if (!currentVehicleUserRepository.existsByVehicle_Id(vehicle.getId())) {
                    myAll.add(vehicle);
                }
            }
            return vehicleMapper.vehiclesDTOtoVehiclesResponseDTO(myAll);
        } else throw new EntityNotFoundException("błąd zasobu: użytkownik");
    }

    public void deleteShareVehicleListByVehicleId(Long id) {
        if (vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Bład przetwarzania"));
            if (currentVehicleUserRepository.existsByVehicle_Id(vehicle.getId())) {
                CurrentVehicleUser currentVehicleUser = currentVehicleUserRepository.findByVehicle(vehicle);
                currentVehicleUserRepository.deleteById(currentVehicleUser.getId());
            } else throw new EntityNotFoundException("błąd zasobu: share");
        } else throw new EntityNotFoundException("błąd zasobu: pojazd");
    }
}
