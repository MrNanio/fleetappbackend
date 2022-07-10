package pl.nankiewic.fleetappbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;
import pl.nankiewic.fleetappbackend.mapper.RefuelingMapper;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleRefuelingRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

class RefuelingServiceTest {
    @Mock
    CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    @Mock
    VehicleRefuelingRepository vehicleRefuelingRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RefuelingMapper refuelingMapper;

    RefuelingService refuelingService;

    private static final String EXAMPLE_EMAIL_ADDRESS = "example@example.com";
    private static final Long EXAMPLE_ID = 1L;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        refuelingService = new RefuelingService(
                checkExistAndPermissionComponent,
                vehicleRefuelingRepository,
                userRepository,
                refuelingMapper);
    }

    @Test
    void should_create_vehicle_refueling() {
        //given
        RefuelingDTO refuelingDTO = RefuelingDTO.builder()
                .id(1L)
                .vehicleId(1L)
                .userId(1L)
                .cost(BigDecimal.valueOf(12.4))
                .description("bvjgfgjf")
                .refuelingDate(LocalDate.now())
                .litre("23").build();
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        when(refuelingMapper.refuelingDtoToVehicleRefuelingEntity(any())).thenReturn(VehicleRefueling.builder().build());
        when(userRepository.findUserByEmail(any())).thenReturn(User.builder().build());
        //when
        refuelingService.createVehicleRefueling(refuelingDTO, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRefuelingRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_refueling() {
        //given
        RefuelingDTO refuelingDTO = RefuelingDTO.builder()
                .id(1L)
                .vehicleId(1L)
                .userId(1L)
                .cost(BigDecimal.valueOf(12.4))
                .description("bvjgfgjf")
                .refuelingDate(LocalDate.now())
                .litre("23").build();
        when(checkExistAndPermissionComponent.accessToRefueling(any(), any())).thenReturn(true);
        when(vehicleRefuelingRepository.findById(any())).thenReturn(Optional.of(VehicleRefueling.builder().build()));
        //when
        refuelingService.updateVehicleRefueling(refuelingDTO, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRefuelingRepository, times(1)).save(any());
    }

    @Test
    void should_get_refueling_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToRefueling(any(), any())).thenReturn(true);
        //when
        refuelingService.getRefuelingById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRefuelingRepository, times(1)).findVehicleRefuelingById(any());
    }

    @Test
    void should_get_refueling_by_vehicle() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        refuelingService.getRefuelingByVehicle(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRefuelingRepository, times(1)).findRefuelingListByVehicle(any());
    }

    @Test
    void should_get_refueling_by_user() {
        //given
        when(userRepository.existsByEmail(any())).thenReturn(true);
        //when
        refuelingService.getRefuelingByUser(EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRefuelingRepository, times(1)).findRefuelingListByUsersVehicle(any());
    }

    @Test
    void should_get_refueling_by_author() {
        //given
        when(userRepository.existsByEmail(any())).thenReturn(true);
        //when
        refuelingService.getRefuelingByAuthor(EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRefuelingRepository, times(1)).findRefuelingListByUser(any());
    }

    @Test
    void should_get_refueling_by_user_and_vehicle() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        when(userRepository.existsById(any())).thenReturn(true);
        //when
        refuelingService.getRefuelingByUserAndVehicle(EXAMPLE_ID, EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRefuelingRepository, times(1)).findAllByVehicleAndUser(any(), any());
    }

    @Test
    void should_delete_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToRefueling(any(), any())).thenReturn(true);
        //when
        refuelingService.deleteRefuelingById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRefuelingRepository, times(1)).deleteById(any());
    }
}