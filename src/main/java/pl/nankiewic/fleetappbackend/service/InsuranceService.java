package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceModifyDTO;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceView;
import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;
import pl.nankiewic.fleetappbackend.mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.nankiewic.fleetappbackend.exceptions.ExceptionConstant.ENTITY_NOT_FOUND_ERROR;

@AllArgsConstructor
@Service
public class InsuranceService {

    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final InsuranceMapper insuranceMapper;

    public InsuranceModifyDTO createVehicleInsurance(InsuranceModifyDTO insuranceModifyDTO) {
        return Optional.of(insuranceModifyDTO)
                .map(insuranceMapper::insuranceDtoToVehicleInsuranceEntity)
                .map(vehicleInsuranceRepository::save)
                .map(insuranceMapper::insuranceToInsuranceModifyDTO)
                .orElseThrow();
    }

    public InsuranceModifyDTO updateVehicleInsurance(InsuranceModifyDTO insuranceModifyDTO) {
        return vehicleInsuranceRepository.findById(insuranceModifyDTO.getId())
                .map(insurance -> insuranceMapper.updateVehicleInsuranceFromDto(insurance, insuranceModifyDTO))
                .map(vehicleInsuranceRepository::save)
                .map(insuranceMapper::insuranceToInsuranceModifyDTO)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_ERROR));
    }

    public InsuranceView getInsuranceViewByInsuranceId(Long insuranceId) {
        return vehicleInsuranceRepository.findInsuranceViewById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_ERROR));
    }

    public List<InsuranceView> getInsurancesByVehicle(Long id) {
            return vehicleInsuranceRepository.findAllInsuranceByVehicle(id);
    }

    public List<InsuranceView> getInsurancesByUser() {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleInsuranceRepository.findAllInsuranceByUsersVehicle(userId);
    }

    public Set<InsuranceType> getInsuranceTypes() {
        return Arrays.stream(InsuranceType.values())
                .collect(Collectors.toSet());
    }

    public void deleteInsuranceById(Long id) {
        vehicleInsuranceRepository.deleteById(id);
    }

}
