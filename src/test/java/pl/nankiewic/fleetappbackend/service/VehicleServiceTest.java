package pl.nankiewic.fleetappbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleDTO;
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
                userRepository,
                vehicleMapper,
                checkExistAndPermissionComponent
        );

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void should_create_vehicle() {
        //given
        var vehicle = Vehicle.builder().build();
        var vehicleDTO = VehicleDTO.builder().build();
        var user = User.builder().id(EXAMPLE_ID).build();

        when(vehicleMapper.vehicleDTOtoVehicle(any())).thenReturn(vehicle);
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));
        when(vehicleRepository.save(any())).thenReturn(vehicle);
        when(vehicleMapper.entityToDto(any())).thenReturn(vehicleDTO);

        //when
        vehicleService.createVehicle(vehicleDTO);

        //then
        verify(userRepository, times(1)).findUserByEmail(any());
        verify(vehicleMapper, times(1)).vehicleDTOtoVehicle(any());
        verify(vehicleMapper, times(1)).entityToDto(any());
        verify(vehicleRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle() {
        //given
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(Vehicle.builder().id(1L).build()));
        when(vehicleMapper.updateVehicleFromRequest(any(), any())).thenReturn(Vehicle.builder().id(1L).build());
        when(vehicleRepository.save(any())).thenReturn(Vehicle.builder().id(1L).build());
        when(vehicleMapper.entityToDto(any())).thenReturn(VehicleDTO.builder().id(1L).build());
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        vehicleService.updateVehicle(VehicleDTO.builder().id(1L).build());
        //then
        verify(vehicleRepository, times(1)).findById(any());
    }

    @Test
    void should_get_vehicles_by_user() {
        //given
        when(userRepository.existsByEmail(any())).thenReturn(true);
        when(vehicleRepository.existsByUser(any())).thenReturn(true);
        when(vehicleRepository.findVehicleViewsByUser(any())).thenReturn(new ArrayList<>());
        //when
        vehicleService.findVehicleViewsByUser();
        //then
        verify(vehicleRepository, times(1)).findVehicleViewsByUser(any());
    }

    @Test
    void should_get_vehicle_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        vehicleService.getVehicleDataById(EXAMPLE_ID);
        //then
        verify(vehicleRepository, times(1)).findVehicleViewById(any());
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