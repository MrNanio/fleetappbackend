package pl.nankiewic.fleetappbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.InspectionDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleInspection;
import pl.nankiewic.fleetappbackend.Mapper.InspectionMapper;
import pl.nankiewic.fleetappbackend.Repository.VehicleInspectionRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class InspectionService {
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final InspectionMapper inspectionMapper;

    public void createInspection(InspectionDTO inspectionDTO) {
        VehicleInspection vehicleInspection = inspectionMapper.vehicleInspectionDtoToEntity(inspectionDTO);
        vehicleInspectionRepository.save(vehicleInspection);
    }

    public void updateInspection(InspectionDTO inspectionDTO) {
        VehicleInspection vehicleInspection = vehicleInspectionRepository.findById(inspectionDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Inspection not found"));
        inspectionMapper.updateVehicleInspectionFromDto(vehicleInspection, inspectionDTO);
        vehicleInspectionRepository.save(vehicleInspection);
    }

    public Iterable<InspectionDTO> getInspectionByVehicle(Long id) {
        return vehicleInspectionRepository.findAllByVehicle(id);
    }

    public InspectionDTO getInspectionById(Long id) {

        return vehicleInspectionRepository.findInspectionById(id);
    }

    public Iterable<InspectionDTO> getInspectionsByUser(String email) {
        return vehicleInspectionRepository.findAllByVehicleIn(email);
    }

    public void deleteInspectionById(Long id) {
        vehicleInspectionRepository.deleteById(id);
    }
}

