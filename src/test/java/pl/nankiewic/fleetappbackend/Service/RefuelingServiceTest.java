package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;
import pl.nankiewic.fleetappbackend.Mapper.RefuelingMapper;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRefuelingRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RefuelingServiceTest {

    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    VehicleRefuelingRepository vehicleRefuelingRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RefuelingMapper refuelingMapper;

    RefuelingService refuelingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        refuelingService = new RefuelingService(
                vehicleRepository,
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
        when(refuelingMapper.refuelingDtoToVehicleRefuelingEntity(any())).thenReturn(VehicleRefueling.builder().build());
        when(userRepository.findUserByEmail(any())).thenReturn(User.builder().build());
        //when
        refuelingService.createVehicleRefueling(refuelingDTO, "mail");
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
        when(vehicleRefuelingRepository.findById(any())).thenReturn(Optional.of(VehicleRefueling.builder().build()));
        //when
        refuelingService.updateVehicleRefueling(refuelingDTO);
        //then
        verify(vehicleRefuelingRepository, times(1)).save(any());
    }

    @Test
    void should_get_refueling_by_id() {
        when(vehicleRefuelingRepository.existsById(anyLong())).thenReturn(true);

        refuelingService.getRefuelingById(1L);

        verify(vehicleRefuelingRepository, times(1)).findVehicleRefuelingById(any());
    }

    @Test
    void should_get_refueling_by_vehicle() {
        when(vehicleRepository.existsById(any())).thenReturn(true);

        refuelingService.getRefuelingByVehicle(1L);

        verify(vehicleRefuelingRepository, times(1)).findRefuelingListByVehicle(any());
    }

    @Test
    void should_get_refueling_by_user() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        refuelingService.getRefuelingByUser("email@email.com");

        verify(vehicleRefuelingRepository, times(1)).findRefuelingListByUsersVehicle(any());
    }

    @Test
    void should_get_refueling_by_author() {

        when(userRepository.existsByEmail(any())).thenReturn(true);

        refuelingService.getRefuelingByAuthor("email@email.com");

        verify(vehicleRefuelingRepository, times(1)).findRefuelingListByUser(any());
    }

    @Test
    void should_get_refueling_by_user_and_vehicle() {

        when(userRepository.existsById(any())).thenReturn(true);
        when(vehicleRepository.existsById(any())).thenReturn(true);

        refuelingService.getRefuelingByUserAndVehicle(1L, 1L);

        verify(vehicleRefuelingRepository, times(1)).findAllByVehicleAndUser(any(), any());
    }

    @Test
    void should_delete_by_id() {

        refuelingService.deleteRefuelingById(1L);
        verify(vehicleRefuelingRepository, times(1)).deleteById(any());
    }
}