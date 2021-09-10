package pl.nankiewic.fleetappbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.Mapper.RepairMapper;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepairRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class RepairService {
    private final VehicleRepairRepository vehicleRepairRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final RepairMapper repairMapper;

    public void createVehicleRepair(RepairDTO repairDTO) {
        VehicleRepair vehicleRepair = repairMapper.repairDtoToVehicleRepairEntity(repairDTO);
        vehicleRepairRepository.save(vehicleRepair);
    }

    public void updateVehicleRepair(RepairDTO repairDTO) {
        VehicleRepair vehicleRepair = vehicleRepairRepository.findById(repairDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Repair not found"));

        repairMapper.updateVehicleRepairFromDto(vehicleRepair, repairDTO);
        vehicleRepairRepository.save(vehicleRepair);
    }

    public RepairDTO getRepairById(Long id) {
        if (vehicleRepairRepository.existsById(id)) {
            return vehicleRepairRepository.findRepairById(id);
        } else throw new EntityNotFoundException("Repair not found");
    }

    public Iterable<RepairDTO> getRepairsByVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            return vehicleRepairRepository.findAllRepairsByVehicleId(id);
        } else throw new EntityNotFoundException("Vehicle not found");

    }

    public Iterable<RepairDTO> getRepairsByUser(String email) {
        if (userRepository.existsByEmail(email)) {
            return vehicleRepairRepository.findAllRepairByFromUserVehicle(email);
        } else throw new EntityNotFoundException("User not found");
    }

    public void deleteRepairById(Long id) {
        vehicleRepairRepository.deleteById(id);
    }
}
