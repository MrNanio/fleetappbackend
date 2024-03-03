package pl.nankiewic.fleetappbackend.service;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingModifyDTO;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingView;
import pl.nankiewic.fleetappbackend.mapper.RefuelingMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRefuelingRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static pl.nankiewic.fleetappbackend.exceptions.ExceptionConstant.ENTITY_NOT_FOUND_ERROR;

@Service
public class RefuelingService {

    private final VehicleRefuelingRepository refuelingRepository;
    private final RefuelingMapper refuelingMapper;

    public RefuelingService(final VehicleRefuelingRepository refuelingRepository,
                            final RefuelingMapper refuelingMapper) {
        this.refuelingRepository = refuelingRepository;
        this.refuelingMapper = refuelingMapper;
    }

    public RefuelingModifyDTO createVehicleRefueling(RefuelingModifyDTO refuelingModifyDTO) {
        return Optional.of(refuelingModifyDTO)
                .map(this::prepareObject)
                .map(refuelingMapper::refuelingDtoToVehicleRefuelingEntity)
                .map(refuelingRepository::save)
                .map(refuelingMapper::refuelingToRefuelingModify)
                .orElseThrow();
    }

    public RefuelingModifyDTO updateVehicleRefueling(RefuelingModifyDTO refuelingModifyDTO) {
        return refuelingRepository.findById(refuelingModifyDTO.getId())
                .map(refueling -> refuelingMapper.updateVehicleRepairFromDto(refueling, refuelingModifyDTO))
                .map(refuelingRepository::save)
                .map(refuelingMapper::refuelingToRefuelingModify)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_ERROR));
    }

    public RefuelingView getRefuelingById(Long id) {
        return refuelingRepository.findRefuelingViewById(id);
    }

    public List<RefuelingView> getRefuelingByVehicle(Long id) {
        return refuelingRepository.findRefuelingViewsByVehicleId(id);
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
        return refuelingRepository.findRefuelingViewsByVehicleIdAndRefuelingUserId(vehicleId, userId);
    }

    public void deleteRefuelingById(Long id) {
        refuelingRepository.deleteById(id);
    }

    private RefuelingModifyDTO prepareObject(RefuelingModifyDTO refuelingModifyDTO) {
        var userId = JWTokenHelper.getJWTUserId();

        return refuelingModifyDTO.toBuilder()
                .userId(userId)
                .build();
    }

}
