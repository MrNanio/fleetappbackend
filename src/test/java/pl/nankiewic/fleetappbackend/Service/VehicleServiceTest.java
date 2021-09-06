package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
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

    VehicleService vehicleService;

    private static final String EXAMPLE_EMAIL_ADDRESS = "example@example.com";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vehicleService = new VehicleService(
                vehicleRepository,
                vehicleMakeRepository,
                fuelTypeRepository,
                userRepository,
                vehicleMapper);
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
        vehicleService.getVehiclesDataByUser("example@example.com");
        //then
        verify(vehicleRepository, times(1)).findVehiclesDataByUser(any());
    }

    @Test
    void should_get_vehicle_by_id() {
        //given
        when(vehicleRepository.existsById(any())).thenReturn(true);
        //when
        vehicleService.getVehicleDataById(1L);
        //then
        verify(vehicleRepository, times(1)).findVehicleDetailsById(any());
    }

    @Test
    void should_delete_vehicle_by_id() {
        //when
        vehicleService.deleteVehicleById(1L);
        //then
        verify(vehicleRepository, times(1)).deleteById(any());
    }

}