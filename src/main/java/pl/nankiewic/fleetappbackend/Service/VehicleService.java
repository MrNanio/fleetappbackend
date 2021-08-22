package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumVehicleStatus;
import pl.nankiewic.fleetappbackend.Entity.FuelType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.*;
import javax.persistence.EntityNotFoundException;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleStatusRepository vehicleStatusRepository;
    private final VehicleMakeRepository vehicleMakeRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository,
                          VehicleStatusRepository vehicleStatusRepository,
                          VehicleMakeRepository vehicleMakeRepository,
                          FuelTypeRepository fuelTypeRepository,
                          UserRepository userRepository,
                          VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleStatusRepository = vehicleStatusRepository;
        this.vehicleMakeRepository = vehicleMakeRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.userRepository = userRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public Iterable<VehicleDTO> getVehiclesDataByUser(String email) {

        if (vehicleRepository.existsByUser(userRepository.findUserByEmail(email))) {
            if (userRepository.existsByEmail(email)) {
                return vehicleRepository.selectVehiclesDataByUser(userRepository.findUserByEmail(email));
            } else throw new EntityNotFoundException("Nie znaleziono użytkownika");
        } else throw new EntityNotFoundException("Nie znaleziono pojazdów użytkownika");
    }

    public VehicleDTO getVehicleDataById(Long id) {
        if (vehicleRepository.existsById(id)) {
            return vehicleRepository.selectVehicleDetailsById(id);
        } else throw new EntityNotFoundException("Nie znaleziono pojazdu o danym id");
    }


    public Vehicle save(VehicleDTO vehicleDTO, String email) {
        Vehicle vehicle = vehicleMapper.vehicleDTOtoVehicle(vehicleDTO);
        vehicle.setFuelType(fuelTypeRepository.findByEnumName(vehicleDTO.getFuelType()));
        vehicle.setVehicleStatus(vehicleStatusRepository.findByEnumName(parseStringToEnum(vehicleDTO.getVehicleStatus().name())));
        vehicle.setVehicleMake(vehicleMakeRepository.findByName(vehicleDTO.getMake()));
        vehicle.setUser(userRepository.findUserByEmail(email));
        return vehicleRepository.save(vehicle);
    }
    private EnumVehicleStatus parseStringToEnum(String status){
        if(EnumVehicleStatus.ACTIVE.name().equals(status)){
            return EnumVehicleStatus.ACTIVE;
        } else if (EnumVehicleStatus.REPAIR.name().equals(status)){
            return EnumVehicleStatus.REPAIR;
        } else throw new EntityNotFoundException("nie znaleziono parsowanego elementu");
    }

    public void deleteVehicleById(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Iterable<VehicleMake> getMake() {
        return vehicleMakeRepository.findAll();
    }

    public Iterable<FuelType> getFuelType() {
        return fuelTypeRepository.findAll();
    }
}
