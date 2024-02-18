package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceView;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.entity.enums.InsuranceType;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InsuranceService {

    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final InsuranceMapper insuranceMapper;

    public void createVehicleInsurance(InsuranceRequestDTO insuranceRequestDTO) {
        if (checkExistAndPermissionComponent.accessToVehicle(insuranceRequestDTO.getVehicleId())) {
            VehicleInsurance vehicleInsurance = insuranceMapper.insuranceDtoToVehicleInsuranceEntity(insuranceRequestDTO);
            vehicleInsuranceRepository.save(vehicleInsurance);
        } else throw new PermissionDeniedException();
    }

    public void updateVehicleInsurance(InsuranceRequestDTO insuranceRequestDTO) {
        if (checkExistAndPermissionComponent.accessToVehicle(insuranceRequestDTO.getVehicleId())) {
            VehicleInsurance vehicleInsurance = vehicleInsuranceRepository.findById(insuranceRequestDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Insurance not found"));
            insuranceMapper.updateVehicleInsuranceFromDto(vehicleInsurance, insuranceRequestDTO);
            vehicleInsuranceRepository.save(vehicleInsurance);
        } else throw new PermissionDeniedException();
    }

    public InsuranceView getInsuranceById(Long id) {
        if (checkExistAndPermissionComponent.accessToInsurance(id)) {
            return vehicleInsuranceRepository.findInsuranceById(id);
        } else throw new PermissionDeniedException();
    }

    public List<InsuranceView> getInsurancesByVehicle(Long id) {
        if (checkExistAndPermissionComponent.accessToVehicle(id)) {
            return vehicleInsuranceRepository.findAllInsuranceByVehicle(id);
        } else throw new PermissionDeniedException();
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
        if (checkExistAndPermissionComponent.accessToInsurance(id)) {
            vehicleInsuranceRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }

}
