package pl.nankiewic.fleetappbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;
import pl.nankiewic.fleetappbackend.Mapper.RefuelingMapper;
import pl.nankiewic.fleetappbackend.Repository.VehicleRefuelingRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class RefuelingService {

    private final VehicleRepository vehicleRepository;
    private final VehicleRefuelingRepository refuelingRepository;
    private final UserRepository userRepository;
    private final RefuelingMapper refuelingMapper;

    public void createVehicleRefueling(RefuelingDTO refuelingDTO, String email) {
        VehicleRefueling vehicleRefueling = refuelingMapper.refuelingDtoToVehicleRefuelingEntity(refuelingDTO);
        vehicleRefueling.setUser(userRepository.findUserByEmail(email));
        refuelingRepository.save(vehicleRefueling);
    }

    public void updateVehicleRefueling(RefuelingDTO refuelingDTO) {
        VehicleRefueling vehicleRefueling = refuelingRepository.findById(refuelingDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Refueling not found"));
        refuelingMapper.updateVehicleRepairFromDto(vehicleRefueling, refuelingDTO);
        refuelingRepository.save(vehicleRefueling);
    }

    public RefuelingDTO getRefuelingById(Long id) {
        if (refuelingRepository.existsById(id)) {
            return refuelingRepository.findVehicleRefuelingById(id);
        } else throw new EntityNotFoundException("Refueling not found");
    }

    public Iterable<RefuelingDTO> getRefuelingByVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            return refuelingRepository.findRefuelingListByVehicle(id);
        } else throw new EntityNotFoundException("Vehicle not found");
    }

    public Iterable<RefuelingDTO> getRefuelingByUser(String email) {
        if (userRepository.existsByEmail(email)) {
            return refuelingRepository.findRefuelingListByUsersVehicle(email);
        } else throw new EntityNotFoundException("User not found");
    }

    public Iterable<RefuelingDTO> getRefuelingByAuthor(String email) {
        if (userRepository.existsByEmail(email)) {
            return refuelingRepository.findRefuelingListByUser(email);
        } else throw new EntityNotFoundException("User not found");
    }

    public Iterable<RefuelingDTO> getRefuelingByUserAndVehicle(Long userId, Long vehicleId) {
        if (userRepository.existsById(userId) && vehicleRepository.existsById(vehicleId)) {
            return refuelingRepository.findAllByVehicleAndUser(vehicleId, userId);
        } else throw new EntityNotFoundException("User or Vehicle not found");
    }

    public void deleteRefuelingById(Long id) {
        refuelingRepository.deleteById(id);
    }

}
