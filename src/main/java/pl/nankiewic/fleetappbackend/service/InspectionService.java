package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.dto.InspectionDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleInspection;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.InspectionMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleInspectionRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class InspectionService {
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final InspectionMapper inspectionMapper;

    public void createInspection(InspectionDTO inspectionDTO, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, inspectionDTO.getVehicleId())) {
            VehicleInspection vehicleInspection = inspectionMapper.vehicleInspectionDtoToEntity(inspectionDTO);
            vehicleInspectionRepository.save(vehicleInspection);
        } else throw new PermissionDeniedException();

    }

    public void updateInspection(InspectionDTO inspectionDTO, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, inspectionDTO.getVehicleId())) {
            VehicleInspection vehicleInspection = vehicleInspectionRepository.findById(inspectionDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Inspection not found"));
            inspectionMapper.updateVehicleInspectionFromDto(vehicleInspection, inspectionDTO);
            vehicleInspectionRepository.save(vehicleInspection);
        } else throw new PermissionDeniedException();
    }

    public Iterable<InspectionDTO> getInspectionByVehicle(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, id)) {
            return vehicleInspectionRepository.findAllByVehicle(id);
        } else throw new PermissionDeniedException();
    }

    public InspectionDTO getInspectionById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToInspection(email, id)) {
            return vehicleInspectionRepository.findInspectionById(id);
        } else throw new PermissionDeniedException();
    }

    public Iterable<InspectionDTO> getInspectionsByUser(String email) {
        return vehicleInspectionRepository.findAllByVehicleIn(email);
    }

    public void deleteInspectionById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToInspection(email, id)) {
            vehicleInspectionRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }
}

