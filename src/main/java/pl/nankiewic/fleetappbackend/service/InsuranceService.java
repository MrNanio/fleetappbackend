package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.InsuranceDTO;
import pl.nankiewic.fleetappbackend.DTO.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.repository.InsuranceTypeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class InsuranceService {

    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final InsuranceTypeRepository insuranceTypeRepository;
    private final InsuranceMapper insuranceMapper;

    public void createVehicleInsurance(InsuranceRequestDTO insuranceRequestDTO, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, insuranceRequestDTO.getVehicleId())) {
            VehicleInsurance vehicleInsurance = insuranceMapper.insuranceDtoToVehicleInsuranceEntity(insuranceRequestDTO);
            vehicleInsuranceRepository.save(vehicleInsurance);
        } else throw new PermissionDeniedException();
    }

    public void updateVehicleInsurance(InsuranceRequestDTO insuranceRequestDTO, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, insuranceRequestDTO.getVehicleId())) {
            VehicleInsurance vehicleInsurance = vehicleInsuranceRepository.findById(insuranceRequestDTO.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Insurance not found"));
            insuranceMapper.updateVehicleInsuranceFromDto(vehicleInsurance, insuranceRequestDTO);
            vehicleInsuranceRepository.save(vehicleInsurance);
        } else throw new PermissionDeniedException();
    }

    public InsuranceDTO getInsuranceById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToInsurance(email, id)) {
            return vehicleInsuranceRepository.findInsuranceById(id);
        } else throw new PermissionDeniedException();
    }

    public Iterable<InsuranceDTO> getInsurancesByVehicle(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToVehicle(email, id)) {
            return vehicleInsuranceRepository.findAllInsuranceByVehicle(id);
        } else throw new PermissionDeniedException();
    }

    public Iterable<InsuranceDTO> getInsurancesByUser(String email) {
        return vehicleInsuranceRepository.findAllInsuranceByUsersVehicle(email);
    }

    public Iterable<InsuranceTypeDTO> getInsuranceTypes() {
        return insuranceTypeRepository.findInsurancesTypes();
    }

    public void deleteInsuranceById(Long id, String email) {
        if (checkExistAndPermissionComponent.accessToInsurance(email, id)) {
            vehicleInsuranceRepository.deleteById(id);
        } else throw new PermissionDeniedException();
    }

}
