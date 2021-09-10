package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.Mapper.RepairMapper;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepairRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RepairServiceTest {

    @Mock
    VehicleRepairRepository vehicleRepairRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RepairMapper repairMapper;

    RepairService repairService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repairService = new RepairService(
                vehicleRepairRepository,
                vehicleRepository,
                userRepository,
                repairMapper);
    }

    @Test
    void should_create_vehicle_repair() {
        //given
        RepairDTO repairDTO = RepairDTO.builder()
                .id(1L)
                .repairDate(LocalDate.now())
                .vehicleId(1L)
                .cost(BigDecimal.valueOf(12.4))
                .description("bhjvhvh")
                .title("fgfgfg")
                .build();
        //when
        repairService.createVehicleRepair(repairDTO);
        //then
        verify(vehicleRepairRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_repair() {
        //given
        RepairDTO repairDTO = RepairDTO.builder()
                .id(1L)
                .repairDate(LocalDate.now())
                .vehicleId(1L)
                .cost(BigDecimal.valueOf(12.4))
                .description("bhjvhvh")
                .title("fgfgfg")
                .build();
        VehicleRepair repair = VehicleRepair.builder().build();
        when(vehicleRepairRepository.findById(any())).thenReturn(Optional.of(repair));
        //when
        repairService.updateVehicleRepair(repairDTO);
        //then
        verify(vehicleRepairRepository, times(1)).save(any());
    }

    @Test
    void should_get_repair_by_id() {
        //given
        when(vehicleRepairRepository.existsById(any())).thenReturn(true);
        //when
        repairService.getRepairById(1L);
        //then
        verify(vehicleRepairRepository, times(1)).findRepairById(any());
    }

    @Test
    void should_get_repairs_by_vehicle() {
        //given
        when(vehicleRepository.existsById(any())).thenReturn(true);
        //when
        repairService.getRepairsByVehicle(1L);
        //then
        verify(vehicleRepairRepository, times(1)).findAllRepairsByVehicleId(any());

    }

    @Test
    void should_get_repairs_by_user() {
        //given
        when(userRepository.existsByEmail(any())).thenReturn(true);
        //when
        repairService.getRepairsByUser("example@user.pl");
        //then
        verify(vehicleRepairRepository, times(1)).findAllRepairByFromUserVehicle(any());


    }

    @Test
    void should_delete_vehicle_repair_by_id() {
        //given
        //when
        repairService.deleteRepairById(1L);
        //then
        verify(vehicleRepairRepository, times(1)).deleteById(any());
    }

}