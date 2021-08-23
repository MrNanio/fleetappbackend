package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

import java.util.*;

import static org.mockito.Mockito.*;

class VehicleServiceTest {
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    VehicleStatusRepository vehicleStatusRepository;
    @Mock
    VehicleMakeRepository vehicleMakeRepository;
    @Mock
    FuelTypeRepository fuelTypeRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    VehicleMapper vehicleMapper;

    VehicleService vehicleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vehicleService = new VehicleService(vehicleRepository, vehicleStatusRepository,
                vehicleMakeRepository, fuelTypeRepository, userRepository, vehicleMapper);
    }

    @Test
    void should_select_vehicles_by_user() {
        when(userRepository.existsByEmail(any())).thenReturn(true);
        when(vehicleRepository.existsByUser(any())).thenReturn(true);
        when(vehicleRepository.selectVehiclesDataByUser(any())).thenReturn(new ArrayList<>());
        vehicleService.getVehiclesDataByUser("example@example.com");
        verify(vehicleRepository, times(1)).selectVehiclesDataByUser(any());
    }

    @Test
    void should_select_vehicle_by_id() {
        when(vehicleRepository.existsById(any())).thenReturn(true);
        when(vehicleRepository.selectVehicleDetailsById(any())).thenReturn(new VehicleDTO());
        vehicleService.getVehicleDataById(1L);
        verify(vehicleRepository, times(1)).selectVehicleDetailsById(any());
    }

    @Test
    void should_delete_vehicle_by_id() {
        vehicleService.deleteVehicleById(1L);
        verify(vehicleRepository, times(1)).deleteById(any());
    }

    private Collection<SimpleGrantedAuthority> extractRoles(List<String> roles) {
        Set<SimpleGrantedAuthority> role = new HashSet<>();
        roles.forEach(r -> role.add(new SimpleGrantedAuthority(r)));
        return role;
    }

}