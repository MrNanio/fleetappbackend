package pl.nankiewic.fleetappbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.InsuranceDTO;
import pl.nankiewic.fleetappbackend.DTO.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.Mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.Mapper.InsuranceTypeMapper;
import pl.nankiewic.fleetappbackend.Repository.InsuranceTypeRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleInsuranceRepository;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class InsuranceService {

    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final InsuranceTypeRepository insuranceType;
    private final InsuranceMapper insuranceMapper;
    private final InsuranceTypeMapper insuranceTypeMapper;

    public void createVehicleInsurance(InsuranceRequestDTO insuranceRequestDTO) {
        VehicleInsurance vehicleInsurance = insuranceMapper.insuranceDtoToVehicleInsuranceEntity(insuranceRequestDTO);
        vehicleInsuranceRepository.save(vehicleInsurance);
    }

    public void updateVehicleInsurance(InsuranceRequestDTO insuranceRequestDTO) {
        VehicleInsurance vehicleInsurance = vehicleInsuranceRepository.findById(insuranceRequestDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Insurance not found"));
        insuranceMapper.updateVehicleInsuranceFromDto(vehicleInsurance, insuranceRequestDTO);
        vehicleInsuranceRepository.save(vehicleInsurance);
    }

    public InsuranceDTO getInsuranceById(Long id) {
        return vehicleInsuranceRepository.findInsuranceById(id);
    }

    public Iterable<InsuranceDTO> getInsurancesByVehicle(Long id) {
        return vehicleInsuranceRepository.findAllInsuranceByVehicle(id);
    }

    public Iterable<InsuranceDTO> getInsurancesByUser(String email) {
        return vehicleInsuranceRepository.findAllInsuranceByUsersVehicle(email);
    }


    public Iterable<InsuranceTypeDTO> getInsuranceTypes() {
        return insuranceTypeMapper.typesToTypesDTO(insuranceType.findAll());
    }

    public void deleteInsuranceById(Long id) {
        vehicleInsuranceRepository.deleteById(id);
    }

}
