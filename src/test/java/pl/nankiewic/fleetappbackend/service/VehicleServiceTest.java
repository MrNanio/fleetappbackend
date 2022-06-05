package pl.nankiewic.fleetappbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

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

    private static final String EXAMPLE_EMAIL_ADDRESS = "example@example.com";
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
    }

    @Test
    void should_create_vehicle() {
        //given
        when(vehicleMapper.vehicleDTOtoVehicle(any())).thenReturn(Vehicle.builder().build());
        when(userRepository.findUserByEmail(any())).thenReturn(User.builder().build());
        //when
        vehicleService.createVehicle(VehicleRequestResponseDTO.builder().build(), EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(userRepository, times(1)).findUserByEmail(any());
        verify(vehicleMapper, times(1)).vehicleDTOtoVehicle(any());

    }

    @Test
    void should_update_vehicle() {
        //given
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(Vehicle.builder().build()));
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        vehicleService.updateVehicle(VehicleRequestResponseDTO.builder().build(), EXAMPLE_EMAIL_ADDRESS);
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
        vehicleService.getVehiclesDataByUser(EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepository, times(1)).findVehiclesDataByUser(any());
    }

    @Test
    void should_get_vehicle_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        vehicleService.getVehicleDataById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepository, times(1)).findVehicleDetailsById(any());
    }

    @Test
    void should_delete_vehicle_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        vehicleService.deleteVehicleById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepository, times(1)).deleteById(any());
    }

}