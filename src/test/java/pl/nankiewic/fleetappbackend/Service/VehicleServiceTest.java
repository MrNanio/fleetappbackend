package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        vehicleService=new VehicleService(vehicleRepository, vehicleStatusRepository,
                vehicleMakeRepository, fuelTypeRepository, userRepository, vehicleMapper);
    }

    @Test
    void should_find_vehicle_by_id() {
        when(vehicleRepository.existsById(any())).thenReturn(true);
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(new Vehicle()));
        when(vehicleMapper.vehicleToVehicleDTO(any())).thenReturn(new VehicleDTO());
        vehicleService.findById(1L);
        verify(vehicleRepository, times(1)).findById(any());
        verify(vehicleMapper, times(1)).vehicleToVehicleDTO(any());
    }

    @Test
    void should_delete_vehicle_by_id() {
        vehicleService.deleteVehicleById(1L);
        verify(vehicleRepository, times(1)).deleteById(any());
    }

}