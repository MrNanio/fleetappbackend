package pl.nankiewic.fleetappbackend.service;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionModifyDTO;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionView;
import pl.nankiewic.fleetappbackend.mapper.InspectionMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleInspectionRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static pl.nankiewic.fleetappbackend.exceptions.ExceptionConstant.ENTITY_NOT_FOUND_ERROR;

@Service
public class InspectionService {

    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final InspectionMapper inspectionMapper;

    public InspectionService(final VehicleInspectionRepository vehicleInspectionRepository,
                             final InspectionMapper inspectionMapper) {
        this.vehicleInspectionRepository = vehicleInspectionRepository;
        this.inspectionMapper = inspectionMapper;
    }

    public InspectionModifyDTO createInspection(InspectionModifyDTO inspectionModifyDTO) {
        return Optional.of(inspectionModifyDTO)
                .map(inspectionMapper::vehicleInspectionDtoToEntity)
                .map(vehicleInspectionRepository::save)
                .map(inspectionMapper::vehicleInspectionToDto)
                .orElseThrow();

    }

    public InspectionModifyDTO updateInspection(InspectionModifyDTO inspectionModifyDTO) {
        return vehicleInspectionRepository.findById(inspectionModifyDTO.getId())
                .map(inspection -> inspectionMapper.updateVehicleInspectionFromDto(inspection, inspectionModifyDTO))
                .map(vehicleInspectionRepository::save)
                .map(inspectionMapper::vehicleInspectionToDto)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_ERROR));
    }

    public List<InspectionView> getInspectionByVehicle(Long id) {
        return vehicleInspectionRepository.findInspectionViewsByVehicleId(id);
    }

    public InspectionView getInspectionById(Long id) {
        return vehicleInspectionRepository.findInspectionById(id)
                .orElseThrow();
    }

    public List<InspectionView> getInspectionsByUser() {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleInspectionRepository.findInspectionViewsByUserId(userId);
    }

    public void deleteInspectionById(Long id) {
        vehicleInspectionRepository.deleteById(id);
    }

}