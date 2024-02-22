package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionModifyDTO;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionView;
import pl.nankiewic.fleetappbackend.entity.VehicleInspection;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.InspectionMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleInspectionRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
public class InspectionService {
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final InspectionMapper inspectionMapper;

    public void createInspection(InspectionModifyDTO inspectionModifyDTO) {


            VehicleInspection vehicleInspection = inspectionMapper.vehicleInspectionDtoToEntity(inspectionModifyDTO);
            vehicleInspectionRepository.save(vehicleInspection);

    }

    public void updateInspection(InspectionModifyDTO inspectionModifyDTO) {
        if (checkExistAndPermissionComponent.accessToVehicle(inspectionModifyDTO.getVehicleId())) {
            VehicleInspection vehicleInspection = vehicleInspectionRepository.findById(inspectionModifyDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Inspection not found"));
            inspectionMapper.updateVehicleInspectionFromDto(vehicleInspection, inspectionModifyDTO);
            vehicleInspectionRepository.save(vehicleInspection);
        } else throw new PermissionDeniedException();
    }

    public List<InspectionView> getInspectionByVehicle(Long id) {
        if (checkExistAndPermissionComponent.accessToVehicle(id)) {
            return vehicleInspectionRepository.findAllByVehicle(id);
        } else throw new PermissionDeniedException();
    }

    public InspectionView getInspectionById(Long id) {
        if (checkExistAndPermissionComponent.accessToInspection(id)) {
            return vehicleInspectionRepository.findInspectionById(id)
                    .orElseThrow();
        } else throw new PermissionDeniedException();
    }

    public List<InspectionView> getInspectionsByUser() {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleInspectionRepository.findAllByVehicleIn(userId);
    }

    public void deleteInspectionById(Long id) {
        if (checkExistAndPermissionComponent.accessToInspection(id)) {
            vehicleInspectionRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }
}

