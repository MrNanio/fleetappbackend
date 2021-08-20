package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.InspectionDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInspection;
import pl.nankiewic.fleetappbackend.Mapper.InspectionMapper;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleInspectionRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

@Service
public class InspectionService {
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final InspectionMapper inspectionMapper;

    @Autowired
    public InspectionService(VehicleInspectionRepository vehicleInspectionRepository,
                             VehicleRepository vehicleRepository,
                             UserRepository userRepository,
                             InspectionMapper inspectionMapper) {
        this.vehicleInspectionRepository = vehicleInspectionRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.inspectionMapper = inspectionMapper;
    }

    public void saveInspection(InspectionDTO inspectionDTO) {
        VehicleInspection vehicleInspection = inspectionMapper.inspectionDTOtoVehicleInspection(inspectionDTO);
        vehicleInspection.setVehicle(vehicleRepository.findById(inspectionDTO.getVehicleId()).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania")));
        vehicleInspectionRepository.save(vehicleInspection);
    }

    public Iterable<InspectionDTO> getInspectionByVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        return inspectionMapper.vehicleInspectionsToInspectionsDTO(vehicleInspectionRepository.findAllByVehicle(vehicle));
    }

    public InspectionDTO getInspectionById(Long id) {
        return inspectionMapper.vehicleInspectionToInspectionDTO(vehicleInspectionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania")));
    }

    public Iterable<InspectionDTO> getInspectionsByUser(String email) {
        User user = userRepository.findUserByEmail(email);
        Iterable<Vehicle> vehicles = vehicleRepository.findVehiclesByUser(user);
        return inspectionMapper.vehicleInspectionsToInspectionsDTO(vehicleInspectionRepository.findAllByVehicleIn(vehicles));
    }

    public void deleteInspectionById(Long id) {
        vehicleInspectionRepository.deleteById(id);
    }
}

