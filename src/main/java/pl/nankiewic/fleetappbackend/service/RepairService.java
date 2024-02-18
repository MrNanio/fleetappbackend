package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.repair.RepairDTO;
import pl.nankiewic.fleetappbackend.dto.repair.RepairView;
import pl.nankiewic.fleetappbackend.entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.RepairMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRepairRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
public class RepairService {

    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleRepairRepository vehicleRepairRepository;
    private final RepairMapper repairMapper;

    public void createVehicleRepair(RepairDTO repairDTO) {
        if (checkExistAndPermissionComponent.accessToVehicle(repairDTO.getVehicleId())) {
            VehicleRepair vehicleRepair = repairMapper.repairDtoToVehicleRepairEntity(repairDTO);
            vehicleRepairRepository.save(vehicleRepair);
        } else throw new PermissionDeniedException();
    }

    public void updateVehicleRepair(RepairDTO repairDTO) {
        if (checkExistAndPermissionComponent.accessToRepair(repairDTO.getId())) {
            VehicleRepair vehicleRepair = vehicleRepairRepository.findById(repairDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Repair not found"));

            repairMapper.updateVehicleRepairFromDto(vehicleRepair, repairDTO);
            vehicleRepairRepository.save(vehicleRepair);
        } else throw new PermissionDeniedException();
    }

    public RepairView getRepairById(Long id) {
        if (checkExistAndPermissionComponent.accessToRepair(id)) {
            return vehicleRepairRepository.findRepairById(id);
        } else throw new PermissionDeniedException();
    }

    public List<RepairView> getRepairsByVehicle(Long id) {
        if (checkExistAndPermissionComponent.accessToVehicle(id)) {
            return vehicleRepairRepository.findAllRepairsByVehicleId(id);
        } else throw new PermissionDeniedException();
    }

    public List<RepairView> getRepairsByUser() {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleRepairRepository.findAllRepairByFromUserVehicle(userId);
    }

    public void deleteRepairById(Long id) {
        if (checkExistAndPermissionComponent.accessToRepair(id)) {
            vehicleRepairRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }

}