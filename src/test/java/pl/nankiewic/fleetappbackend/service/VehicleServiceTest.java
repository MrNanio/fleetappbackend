package pl.nankiewic.fleetappbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.repository.*;

import java.util.*;

import static org.mockito.Mockito.*;

class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    VehicleMakeRepository vehicleMakeRepository;
    @Mock
    FuelTypeRepository fuelTypeRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    VehicleMapper vehicleMapper;
    @Mock
    CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    VehicleService vehicleService;

    private static final Long EXAMPLE_ID = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleService = new VehicleService(
                vehicleRepository,
                vehicleMakeRepository,
                fuelTypeRepository,
                userRepository,
                vehicleMapper,
                checkExistAndPermissionComponent);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void should_create_vehicle() {
        //given
        when(vehicleMapper.vehicleDTOtoVehicle(any(),any())).thenReturn(Vehicle.builder().build());
        when(vehicleMapper.entityToResponseDto(any())).thenReturn(VehicleRequestResponseDTO.builder().build());
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(User.builder().id(1L).build()));
        when(vehicleRepository.save(any())).thenReturn(Vehicle.builder().build());
        //when
        vehicleService.createVehicle(VehicleRequestResponseDTO.builder().build());
        //then
        verify(userRepository, times(1)).findByEmail(any());
        verify(vehicleMapper, times(1)).vehicleDTOtoVehicle(any(), any());
        verify(vehicleMapper, times(1)).entityToResponseDto(any());
        verify(vehicleRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle() {
        //given
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(Vehicle.builder().build()));
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        vehicleService.updateVehicle(VehicleRequestResponseDTO.builder().build());
        //then
        verify(vehicleRepository, times(1)).findById(any());
    }

    @Test
    void should_get_vehicles_by_user() {
        //given
        when(userRepository.existsByEmail(any())).thenReturn(true);
        when(vehicleRepository.existsByUser(any())).thenReturn(true);
        when(vehicleRepository.findVehiclesDataByUser(any())).thenReturn(new ArrayList<>());
        //when
        vehicleService.getVehiclesDataByUser();
        //then
        verify(vehicleRepository, times(1)).findVehiclesDataByUser(any());
    }

    @Test
    void should_get_vehicle_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        vehicleService.getVehicleDataById(EXAMPLE_ID);
        //then
        verify(vehicleRepository, times(1)).findVehicleDetailsById(any());
    }

    @Test
    void should_delete_vehicle_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        vehicleService.deleteVehicleById(EXAMPLE_ID);
        //then
        verify(vehicleRepository, times(1)).deleteById(any());
    }

}