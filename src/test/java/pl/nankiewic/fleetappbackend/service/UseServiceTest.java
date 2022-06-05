package pl.nankiewic.fleetappbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;
import pl.nankiewic.fleetappbackend.mapper.UseMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

import java.time.LocalDate;
import java.util.Optional;

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
    @Mock
    CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    UseService useService;

    private static final String EXAMPLE_EMAIL_ADDRESS = "example@example.com";
    private static final Long EXAMPLE_ID = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useService = new UseService(
                checkExistAndPermissionComponent,
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
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
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
        when(checkExistAndPermissionComponent.accessToUse(any(), any())).thenReturn(true);
        when(vehicleUseRepository.findById(any())).thenReturn(Optional.of(VehicleUse.builder().trip(Short.parseShort("12")).build()));
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        //when
        useService.updateVehicleUse(useDTO, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleUseRepository, times(1)).save(any());
        verify(useMapper, times(1)).updateVehicleUseFromDto(any(), any());

    }

    @Test
    void should_get_use_by_vehicle() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        when(vehicleRepository.existsById(any())).thenReturn(true);
        //when
        useService.getUseByVehicle(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleUseRepository, times(1)).findAllByVehicleId(any());

    }

    @Test
    void should_get_use_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToUse(any(), any())).thenReturn(true);
        //when
        useService.getUseByUseId(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
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
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        useService.getUseByUserAndVehicle(EXAMPLE_ID, EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleUseRepository, times(1)).findAllByVehicleAndUser(any(), any());
    }

    @Test
    void should_delete_use_by_id() {

        //given
        VehicleUse use = VehicleUse.builder().trip(Short.parseShort("12")).vehicle(Vehicle.builder().mileage("123").build()).build();
        when(vehicleUseRepository.findById(any())).thenReturn(Optional.of(use));
        when(checkExistAndPermissionComponent.accessToUse(any(), any())).thenReturn(true);
        //when
        useService.deleteUseById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepository, times(1)).save(any());
        verify(vehicleUseRepository, times(1)).deleteById(any());

    }
}