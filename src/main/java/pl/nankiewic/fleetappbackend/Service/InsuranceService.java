package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.InsuranceDTO;
import pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.Mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.Mapper.InsuranceTypeMapper;
import pl.nankiewic.fleetappbackend.Repository.InsuranceTypeRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleInsuranceRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

@Service
public class InsuranceService {

    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final InsuranceTypeRepository insuranceType;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final InsuranceMapper mapper;
    private final InsuranceTypeMapper insuranceTypeMapper;

    @Autowired
    public InsuranceService(VehicleInsuranceRepository vehicleInsuranceRepository,
                            InsuranceTypeRepository insuranceType, VehicleRepository vehicleRepository,
                            UserRepository userRepository, InsuranceMapper mapper,
                            InsuranceTypeMapper insuranceTypeMapper) {
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
        this.insuranceType = insuranceType;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.insuranceTypeMapper = insuranceTypeMapper;
    }


    public VehicleInsurance save(InsuranceDTO insuranceDTO) {
        VehicleInsurance vehicleInsurance = mapper.insuranceDTOtoVehicleInsurance(insuranceDTO);
        vehicleInsurance.setVehicle(vehicleRepository.findById(insuranceDTO.getVehicleId()).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania")));
        vehicleInsurance.setInsuranceType(insuranceType.findByName(insuranceDTO.getInsuranceType()));
        return vehicleInsuranceRepository.save(vehicleInsurance);
    }

    public Iterable<InsuranceDTO> getInsurancesByVehicle(Long id) {
        return mapper.vehicleInsurancesToInsurancesDTO(vehicleInsuranceRepository
                .findAllByVehicle((vehicleRepository.findById(id)).orElseThrow(
                        () -> new RuntimeException("Bład przetwarzania"))));
    }

    public Iterable<InsuranceDTO> getInsurancesByUser(String email) {
        User user = userRepository.findUserByEmail(email);
        Iterable<Vehicle> vehicle = vehicleRepository.findVehiclesByUser(user);
        return mapper.vehicleInsurancesToInsurancesDTO(vehicleInsuranceRepository.findAllByVehicleIn(vehicle));
    }

    public InsuranceDTO getInsuranceById(Long id) {
        return mapper.vehicleInsuranceToInsuranceDTO(vehicleInsuranceRepository.findById(id).orElse(null));
    }

    public Iterable<InsuranceTypeDTO> getInsuranceTypes() {
        return insuranceTypeMapper.typesToTypesDTO(insuranceType.findAll());
    }

    public void deleteInsuranceById(Long id) {
        vehicleInsuranceRepository.deleteById(id);
    }


}
