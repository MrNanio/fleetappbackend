package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;
import pl.nankiewic.fleetappbackend.Mapper.UseMapper;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleUseRepository;

import javax.persistence.EntityNotFoundException;


@Service
public class UseService {
    VehicleUseRepository vehicleUseRepository;
    VehicleRepository vehicleRepository;
    UserRepository userRepository;
    UseMapper mapper;
    @Autowired
    public UseService(VehicleUseRepository vehicleUseRepository, VehicleRepository vehicleRepository,
                      UserRepository userRepository, UseMapper mapper) {
        this.vehicleUseRepository = vehicleUseRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void save(UseDTO use, String email){
        VehicleUse vehicleUse = mapper.useDTOtoVehicleUse(use);
        vehicleUse.setVehicle(vehicleRepository.findById(use.getVehicleId()).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania")));
        vehicleUse.setUser(userRepository.findUserByEmail(email));
        vehicleUseRepository.save(vehicleUse);
    }
    /*
    get by vehicle
    only for vehicle owner
     */
    public Iterable<UseDTO> getUseByVehicle(Long id) {
        if(vehicleRepository.existsById(id)) {
            return mapper.vehicleUseToUseDTO(vehicleUseRepository.findAllByVehicle(
                    vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Bład przetwarzania"))));
        } else throw new EntityNotFoundException();
    }
    /*
    get by author of use
    for all
     */
    public Iterable <UseDTO> getUseByUser(String email){
        return mapper.vehicleUseToUseDTO(vehicleUseRepository.findAllByUser(userRepository.findUserByEmail(email)));
    }
    /*
    get by use id
     */
    public UseDTO getUseById(Long id){
        if(vehicleUseRepository.existsById(id)) {
            return mapper.vehicleUseToUseDTO(vehicleUseRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Bład przetwarzania")));
        } else throw new EntityNotFoundException();
    }
    public void deleteUseById(Long id){
        vehicleUseRepository.deleteById(id);
    }
}
