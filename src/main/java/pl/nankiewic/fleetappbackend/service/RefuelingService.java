package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.RefuelingMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRefuelingRepository;
import pl.nankiewic.fleetappbackend.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class RefuelingService {

    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleRefuelingRepository refuelingRepository;
    private final UserRepository userRepository;
    private final RefuelingMapper refuelingMapper;

    public void createVehicleRefueling(RefuelingDTO refuelingDTO, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, refuelingDTO.getVehicleId())){
            VehicleRefueling vehicleRefueling = refuelingMapper.refuelingDtoToVehicleRefuelingEntity(refuelingDTO);
            vehicleRefueling.setUser(userRepository.findUserByEmail(email));
            refuelingRepository.save(vehicleRefueling);
        }  else throw new PermissionDeniedException();
    }

    public void updateVehicleRefueling(RefuelingDTO refuelingDTO, String email) {
        if (checkExistAndPermissionComponent.accessToRefueling(email, refuelingDTO.getVehicleId())) {
            VehicleRefueling vehicleRefueling = refuelingRepository.findById(refuelingDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Refueling not found"));
            refuelingMapper.updateVehicleRepairFromDto(vehicleRefueling, refuelingDTO);
            refuelingRepository.save(vehicleRefueling);
        } else throw new PermissionDeniedException();
    }

    public RefuelingDTO getRefuelingById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToRefueling(email, id)) {
            return refuelingRepository.findVehicleRefuelingById(id);
        } else throw new PermissionDeniedException();
    }

    public Iterable<RefuelingDTO> getRefuelingByVehicle(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, id)) {
            return refuelingRepository.findRefuelingListByVehicle(id);
        } else throw new PermissionDeniedException();
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

    public Iterable<RefuelingDTO> getRefuelingByUserAndVehicle(Long userId, Long vehicleId, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, vehicleId)) {
            if (userRepository.existsById(userId)) {
                return refuelingRepository.findAllByVehicleAndUser(vehicleId, userId);
            } else throw new EntityNotFoundException("User not found");
        } else throw new PermissionDeniedException();
    }

    public void deleteRefuelingById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToRefueling(email, id)) {
            refuelingRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }

}
