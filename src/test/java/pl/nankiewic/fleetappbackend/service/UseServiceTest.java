package pl.nankiewic.fleetappbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.nankiewic.fleetappbackend.config.security.CustomUserDetails;
import pl.nankiewic.fleetappbackend.dto.use.UseDTO;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleUse;
import pl.nankiewic.fleetappbackend.entity.enums.TripType;
import pl.nankiewic.fleetappbackend.mapper.UseMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleUseRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UseServiceTest {

    @Mock
    VehicleUseRepository vehicleUseRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    UseMapper useMapper;
    @Mock
    CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    UseService useService;

    private static final Long EXAMPLE_ID = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useService = new UseService(
                checkExistAndPermissionComponent,
                vehicleUseRepository,
                vehicleRepository,
                useMapper);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        CustomUserDetails principal = CustomUserDetails.builder().id(1L).build();
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void should_create_vehicle_use() {
        //given
        var useDTO = UseDTO.builder()
                .id(1L)
                .vehicleId(1L)
                .userId(1L)
                .tripDate(LocalDate.of(2021, 12, 12))
                .trip(Short.parseShort("12"))
                .description("Example")
                .tripType(TripType.CITY)
                .build();
        var vehicle = Vehicle.builder()
                .mileage("123")
                .build();
        var vehicleUse = VehicleUse.builder()
                .build();

        when(checkExistAndPermissionComponent.accessToVehicle(any())).thenReturn(true);
        when(vehicleUseRepository.save(any())).thenReturn(vehicleUse);
        when(useMapper.vehicleUseDtoToEntity(any())).thenReturn(vehicleUse);
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        //when
        useService.createVehicleUse(useDTO);
        //then
        verify(vehicleUseRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_use() {
        //given
        var useDTO = UseDTO.builder()
                .id(1L)
                .vehicleId(1L)
                .userId(1L)
                .tripDate(LocalDate.of(2021, 12, 12))
                .trip(Short.parseShort("12"))
                .description("Example")
                .tripType(TripType.CITY)
                .build();
        var vehicle = Vehicle.builder()
                .mileage("123").build();
        when(checkExistAndPermissionComponent.accessToUse(any())).thenReturn(true);
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
        when(checkExistAndPermissionComponent.accessToVehicle(any())).thenReturn(true);
        when(vehicleRepository.existsById(any())).thenReturn(true);
        //when
        useService.getUseByVehicle(EXAMPLE_ID);
        //then
        verify(vehicleUseRepository, times(1)).findAllByVehicleId(any());
    }

    @Test
    void should_get_use_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToUse(any())).thenReturn(true);
        //when
        useService.getUseByUseId(EXAMPLE_ID);
        //then
        verify(vehicleUseRepository, times(1)).findByUseId(any());
    }

    @Test
    void should_get_use_by_user() {
        //when
        useService.getUseByUser();
        //then
        verify(vehicleUseRepository, times(1)).findAllByUserId(any());
    }

    @Test
    void should_get_use_by_user_and_vehicle() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any())).thenReturn(true);
        //when
        useService.getUseByUserAndVehicle(EXAMPLE_ID, EXAMPLE_ID);
        //then
        verify(vehicleUseRepository, times(1)).findAllByVehicleAndUser(any(), any());
    }

    @Test
    void should_delete_use_by_id() {
        //given
        var vehicle = Vehicle.builder()
                .mileage("123")
                .build();
        var use = VehicleUse.builder()
                .trip(Short.parseShort("12"))
                .vehicle(vehicle)
                .build();

        when(vehicleUseRepository.findById(any())).thenReturn(Optional.of(use));
        when(checkExistAndPermissionComponent.accessToUse(any())).thenReturn(true);
        //when
        useService.deleteUseById(EXAMPLE_ID);
        //then
        verify(vehicleRepository, times(1)).save(any());
        verify(vehicleUseRepository, times(1)).deleteById(any());
    }

}