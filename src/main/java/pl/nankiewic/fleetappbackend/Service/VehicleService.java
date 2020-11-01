package pl.nankiewic.fleetappbackend.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

import javax.persistence.EntityNotFoundException;


@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private VehicleStatusRepository vehicleStatusRepository;
    private VehicleMakeRepository vehicleMakeRepository;
    private FuelTypeRepository fuelTypeRepository;
    private UserRepository userRepository;
    private VehicleMapper mapper;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleStatusRepository vehicleStatusRepository,
                          VehicleMakeRepository vehicleMakeRepository, FuelTypeRepository fuelTypeRepository,
                          UserRepository userRepository, VehicleMapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleStatusRepository = vehicleStatusRepository;
        this.vehicleMakeRepository = vehicleMakeRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public Iterable<VehicleDTO> findVehiclesByUser(String email) {
        if (vehicleRepository.existsByUser(userRepository.findUserByEmail(email))) {
            return mapper.map(vehicleRepository.findVehiclesByUser(userRepository.findUserByEmail(email)));
        } else throw new EntityNotFoundException("Brak elementów");

    }

    public VehicleDTO findById(Long id) {
        if (vehicleRepository.existsById(id)) {
            return mapper.vehicleToVehicleDTO(vehicleRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Bład przetwarzania")));
        } else throw new EntityNotFoundException("Pojazd nie istnieje");

    }

    public Vehicle save(VehicleDTO vehicleDTO, String email) {
        Vehicle vehicle = mapper.vehicleDTOtoVehicle(vehicleDTO);
        vehicle.setFuelType(fuelTypeRepository.findByName(vehicleDTO.getFuelType()));
        vehicle.setVehicleStatus(vehicleStatusRepository.findByStatusName(vehicleDTO.getVehicleStatus()));
        vehicle.setVehicleMake(vehicleMakeRepository.findByName(vehicleDTO.getMake()));
        vehicle.setUser(userRepository.findUserByEmail(email));
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicleById(Long id) {
        vehicleRepository.deleteById(id);
    }

/*
    public Iterable<Vehicle> findAllVehicles(String email) {
        User user =userRepository.findUserByEmail(email);
        List<Vehicle> myAll = new ArrayList<Vehicle>();
        Iterable<Vehicle> myByOwn =vehicleRepository.findVehiclesByUser(user);
        for (Vehicle vehicle : myByOwn) {
            myAll.add(vehicle);
        }
        Iterable<CurrentVehicleUser> myByShare = currentVehicleUserRepository.findCurrentVehicleUsersByUserIs(user);
        for (CurrentVehicleUser currentVehicleUser : myByShare) {
            myAll.add(currentVehicleUser.getVehicle());
        }
        return myAll;
    }*/


}
