package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;
import pl.nankiewic.fleetappbackend.Mapper.UseMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class UseServiceTest {

    @Mock
    VehicleUseRepository vehicleUseRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    UseMapper useMapper;

    UseService useService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useService = new UseService(
                vehicleUseRepository,
                vehicleRepository,
                userRepository,
                useMapper);
    }

    @Test
    void should_create_vehicle_use() {
        //given
        UseDTO useDTO = UseDTO.builder()
                .id(1L)
                .vehicleId(1L)
                .userId(1L)
                .tripDate(LocalDate.of(2021, 12, 12))
                .trip(Short.parseShort("12"))
                .description("Example")
                .tripType("MIEJSKI")
                .build();

        String email = "mail@mail.com";
        Vehicle vehicle = Vehicle.builder()
                .mileage("123").build();
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        when(useMapper.vehicleUseDtoToEntity(any())).thenReturn(VehicleUse.builder().build());
        //when
        useService.createVehicleUse(useDTO, email);
        //then
        verify(vehicleUseRepository, times(1)).save(any());

    }

    @Test
    void should_update_vehicle_use() {
        //given
        UseDTO useDTO = UseDTO.builder()
                .id(1L)
                .vehicleId(1L)
                .userId(1L)
                .tripDate(LocalDate.of(2021, 12, 12))
                .trip(Short.parseShort("12"))
                .description("Example")
                .tripType("MIEJSKI")
                .build();
        Vehicle vehicle = Vehicle.builder()
                .mileage("123").build();
        when(vehicleUseRepository.findById(any())).thenReturn(Optional.of(VehicleUse.builder().trip(Short.parseShort("12")).build()));
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        //when
        useService.updateVehicleUse(useDTO);
        //then
        verify(vehicleUseRepository, times(1)).save(any());
        verify(useMapper, times(1)).updateVehicleUseFromDto(any(), any());

    }

    @Test
    void should_get_use_by_vehicle() {
        //given
        when(vehicleRepository.existsById(any())).thenReturn(true);
        //when
        useService.getUseByVehicle(1L);
        //then
        verify(vehicleUseRepository, times(1)).findAllByVehicleId(any());

    }

    @Test
    void should_get_use_by_id() {
        //when
        useService.getUseByUseId(1L);
        //then
        verify(vehicleUseRepository, times(1)).findByUseId(any());
    }

    @Test
    void should_get_use_by_user() {
        //given
        when(userRepository.findUserByEmail(any())).thenReturn(User.builder().id(1L).build());
        //when
        useService.getUseByUser("mail@mail.com");
        //then
        verify(vehicleUseRepository, times(1)).findAllByUserId(any());
    }

    @Test
    void should_get_use_by_user_and_vehicle() {
        //given
        when(userRepository.existsById(any())).thenReturn(true);
        when(vehicleRepository.existsById(any())).thenReturn(true);
        //when
        useService.getUseByUserAndVehicle(1L, 1L);
        //then
        verify(vehicleUseRepository, times(1)).findAllByVehicleAndUser(any(), any());
    }

    @Test
    void should_delete_use_by_id() {

        //given
        VehicleUse use = VehicleUse.builder().trip(Short.parseShort("12")).vehicle(Vehicle.builder().mileage("123").build()).build();
        when(vehicleUseRepository.findById(any())).thenReturn(Optional.of(use));
        //when
        useService.deleteUseById(1L);
        //then
        verify(vehicleRepository, times(1)).save(any());
        verify(vehicleUseRepository, times(1)).deleteById(any());

    }
}