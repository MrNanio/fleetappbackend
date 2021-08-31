package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Mapper.VehicleMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

import java.math.BigDecimal;
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
    void should_create_vehicle(){
        //given
        VehicleRequestResponseDTO vehicleRequestResponseDTO = VehicleRequestResponseDTO.builder()
                .id(1L)
                .make("SKODA")
                .model("FABIA")
                .year("2009")
                .color("BIAŁY")
                .mileage("209000")
                .vinNumber("GTFRED4567DEY65TG")
                .vehicleRegistrationNumber("LU7654D")
                .fuelType("ON")
                .cityFuelConsumption(BigDecimal.valueOf(5.6))
                .countryFuelConsumption(BigDecimal.valueOf(3.6))
                .averageFuelConsumption(BigDecimal.valueOf(4.6))
                .vehicleStatus("ACTIVE")
                .build();
        Vehicle vehicle = Vehicle.builder()
                .id(1L)
                .build();

        User user = User.builder().id(1L).build();

        String email="emeila@hdh.pl";
        when(vehicleMapper.vehicleDTOtoVehicle(any())).thenReturn(vehicle);
        when(userRepository.findUserByEmail(any())).thenReturn(user);
        //when
        vehicleService.createVehicle(vehicleRequestResponseDTO, email);
        //then
        verify(userRepository, times(1)).findUserByEmail(any());

    }

    @Test
    void should_select_vehicles_by_user() {
        //given
        when(userRepository.existsByEmail(any())).thenReturn(true);
        when(vehicleRepository.existsByUser(any())).thenReturn(true);
        when(vehicleRepository.findVehiclesDataByUser(any())).thenReturn(new ArrayList<>());
        //when
        vehicleService.getVehiclesDataByUser("example@example.com");
        //then
        verify(vehicleRepository, times(1)).findVehiclesDataByUser(any());
    }

  //  @Test
    void should_select_vehicle_by_id() {
        when(vehicleRepository.existsById(any())).thenReturn(true);
       // when(vehicleRepository.selectVehicleDetailsById(any())).thenReturn(new VehicleDTO());
        vehicleService.getVehicleDataById(1L);
        verify(vehicleRepository, times(1)).findVehicleDetailsById(any());
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