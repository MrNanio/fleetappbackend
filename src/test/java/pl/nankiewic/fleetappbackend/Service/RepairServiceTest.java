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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

class RepairServiceTest {
    @Mock
    CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    @Mock
    VehicleRepairRepository vehicleRepairRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RepairMapper repairMapper;

    RepairService repairService;

    private static final String EXAMPLE_EMAIL_ADDRESS = "example@example.com";
    private static final Long EXAMPLE_ID = 1L;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repairService = new RepairService(
                checkExistAndPermissionComponent,
                vehicleRepairRepository,
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
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        repairService.createVehicleRepair(repairDTO, EXAMPLE_EMAIL_ADDRESS);
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
        when(checkExistAndPermissionComponent.accessToRepair(any(), any())).thenReturn(true);
        when(vehicleRepairRepository.findById(any())).thenReturn(Optional.of(repair));
        //when
        repairService.updateVehicleRepair(repairDTO, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepairRepository, times(1)).save(any());
    }

    @Test
    void should_get_repair_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToRepair(any(), any())).thenReturn(true);
        //when
        repairService.getRepairById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepairRepository, times(1)).findRepairById(any());
    }

    @Test
    void should_get_repairs_by_vehicle() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        repairService.getRepairsByVehicle(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepairRepository, times(1)).findAllRepairsByVehicleId(any());

    }

    @Test
    void should_get_repairs_by_user() {
        //given
        when(userRepository.existsByEmail(any())).thenReturn(true);
        //when
        repairService.getRepairsByUser(EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepairRepository, times(1)).findAllRepairByFromUserVehicle(any());


    }

    @Test
    void should_delete_vehicle_repair_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToRepair(any(), any())).thenReturn(true);
        //when
        repairService.deleteRepairById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleRepairRepository, times(1)).deleteById(any());
    }

}