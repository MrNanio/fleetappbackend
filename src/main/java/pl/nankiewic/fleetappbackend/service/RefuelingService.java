package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingDTO;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingView;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.RefuelingMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRefuelingRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
public class RefuelingService {

    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleRefuelingRepository refuelingRepository;
    private final RefuelingMapper refuelingMapper;

    public void createVehicleRefueling(RefuelingDTO refuelingDTO) {
        if (checkExistAndPermissionComponent.accessToVehicle(refuelingDTO.getVehicleId())){
            var userId = JWTokenHelper.getJWTUserId();
            var user = User.builder()
                    .id(userId)
                    .build();

            VehicleRefueling vehicleRefueling = refuelingMapper.refuelingDtoToVehicleRefuelingEntity(refuelingDTO);
            vehicleRefueling.setUser(user);
            refuelingRepository.save(vehicleRefueling);
        }  else throw new PermissionDeniedException();
    }

    public void updateVehicleRefueling(RefuelingDTO refuelingDTO) {
        if (checkExistAndPermissionComponent.accessToRefueling(refuelingDTO.getVehicleId())) {
            VehicleRefueling vehicleRefueling = refuelingRepository.findById(refuelingDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Refueling not found"));
            refuelingMapper.updateVehicleRepairFromDto(vehicleRefueling, refuelingDTO);
            refuelingRepository.save(vehicleRefueling);
        } else throw new PermissionDeniedException();
    }

    public RefuelingView getRefuelingById(Long id) {
        if (checkExistAndPermissionComponent.accessToRefueling(id)) {
            return refuelingRepository.findRefuelingViewById(id);
        } else throw new PermissionDeniedException();
    }

    public List<RefuelingView> getRefuelingByVehicle(Long id) {
        if (checkExistAndPermissionComponent.accessToVehicle(id)) {
            return refuelingRepository.findRefuelingViewsByVehicleId(id);
        } else throw new PermissionDeniedException();
    }

    public List<RefuelingView> getRefuelingByUser() {
        var userId = JWTokenHelper.getJWTUserId();

        return refuelingRepository.findRefuelingViewsByVehicleOwnerId(userId);
    }

    public List<RefuelingView> getRefuelingByAuthor() {
        var userId = JWTokenHelper.getJWTUserId();

        return refuelingRepository.findRefuelingViewsByRefuelingUserId(userId);
    }

    public List<RefuelingView> getRefuelingByUserAndVehicle(Long userId, Long vehicleId) {
        if (checkExistAndPermissionComponent.accessToVehicle(vehicleId)) {
            return refuelingRepository.findRefuelingViewsByVehicleIdAndRefuelingUserId(vehicleId, userId);
        } else throw new PermissionDeniedException();
    }

    public void deleteRefuelingById(Long id) {
        if (checkExistAndPermissionComponent.accessToRefueling(id)) {
            refuelingRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }

}
