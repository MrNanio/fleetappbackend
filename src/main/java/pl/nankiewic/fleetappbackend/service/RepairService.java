package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.RepairMapper;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRepairRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class RepairService {
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleRepairRepository vehicleRepairRepository;
    private final UserRepository userRepository;
    private final RepairMapper repairMapper;

    public void createVehicleRepair(RepairDTO repairDTO, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, repairDTO.getVehicleId())) {
            VehicleRepair vehicleRepair = repairMapper.repairDtoToVehicleRepairEntity(repairDTO);
            vehicleRepairRepository.save(vehicleRepair);
        } else throw new PermissionDeniedException();
    }

    public void updateVehicleRepair(RepairDTO repairDTO, String email) {
        if (checkExistAndPermissionComponent.accessToRepair(email, repairDTO.getId())) {
            VehicleRepair vehicleRepair = vehicleRepairRepository.findById(repairDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Repair not found"));

            repairMapper.updateVehicleRepairFromDto(vehicleRepair, repairDTO);
            vehicleRepairRepository.save(vehicleRepair);
        } else throw new PermissionDeniedException();
    }

    public RepairDTO getRepairById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToRepair(email, id)) {
            return vehicleRepairRepository.findRepairById(id);
        } else throw new PermissionDeniedException();
    }

    public Iterable<RepairDTO> getRepairsByVehicle(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, id)) {
            return vehicleRepairRepository.findAllRepairsByVehicleId(id);
        } else throw new PermissionDeniedException();
    }

    public Iterable<RepairDTO> getRepairsByUser(String email) {
        if (userRepository.existsByEmail(email)) {
            return vehicleRepairRepository.findAllRepairByFromUserVehicle(email);
        } else throw new EntityNotFoundException("User not found");
    }

    public void deleteRepairById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToRepair(email, id)) {
            vehicleRepairRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }
}
