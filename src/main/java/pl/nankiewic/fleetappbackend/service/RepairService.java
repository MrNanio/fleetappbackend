package pl.nankiewic.fleetappbackend.service;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.repair.RepairModifyDTO;
import pl.nankiewic.fleetappbackend.dto.repair.RepairView;
import pl.nankiewic.fleetappbackend.mapper.RepairMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRepairRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static pl.nankiewic.fleetappbackend.exceptions.ExceptionConstant.ENTITY_NOT_FOUND_ERROR;

@Service
public class RepairService {

    private final VehicleRepairRepository vehicleRepairRepository;
    private final RepairMapper repairMapper;

    public RepairService(final VehicleRepairRepository vehicleRepairRepository,
                         final RepairMapper repairMapper) {
        this.vehicleRepairRepository = vehicleRepairRepository;
        this.repairMapper = repairMapper;
    }

    public RepairModifyDTO createVehicleRepair(RepairModifyDTO repairModifyDTO) {
        return Optional.of(repairModifyDTO)
                .map(repairMapper::repairDtoToVehicleRepairEntity)
                .map(vehicleRepairRepository::save)
                .map(repairMapper::vehicleRepairToRepairModifyDTO)
                .orElseThrow();
    }

    public RepairModifyDTO updateVehicleRepair(RepairModifyDTO repairModifyDTO) {
        return vehicleRepairRepository.findById(repairModifyDTO.getId())
                .map(repair -> repairMapper.updateVehicleRepairFromDto(repair, repairModifyDTO))
                .map(vehicleRepairRepository::save)
                .map(repairMapper::vehicleRepairToRepairModifyDTO)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_ERROR));
    }

    public RepairView getRepairViewById(Long id) {
        return vehicleRepairRepository.findRepairById(id)
                .orElseThrow();
    }

    public List<RepairView> getRepairViewsByVehicleId(Long vehicleId) {
        return vehicleRepairRepository.findAllRepairsByVehicleId(vehicleId);
    }

    public List<RepairView> getRepairsByUser() {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleRepairRepository.findAllRepairByFromUserVehicle(userId);
    }

    public void deleteRepairById(Long id) {
        vehicleRepairRepository.deleteById(id);
    }

}