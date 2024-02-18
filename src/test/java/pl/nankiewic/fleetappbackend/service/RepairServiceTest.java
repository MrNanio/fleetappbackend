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
import pl.nankiewic.fleetappbackend.dto.repair.RepairDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.mapper.RepairMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRepairRepository;

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
    RepairMapper repairMapper;

    RepairService repairService;

    private static final Long EXAMPLE_ID = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repairService = new RepairService(
                checkExistAndPermissionComponent,
                vehicleRepairRepository,
                repairMapper);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        CustomUserDetails principal = CustomUserDetails.builder().id(1L).build();
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);
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
        when(checkExistAndPermissionComponent.accessToVehicle(any())).thenReturn(true);
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
        when(checkExistAndPermissionComponent.accessToRepair(any())).thenReturn(true);
        when(vehicleRepairRepository.findById(any())).thenReturn(Optional.of(repair));
        //when
        repairService.updateVehicleRepair(repairDTO);
        //then
        verify(vehicleRepairRepository, times(1)).save(any());
    }

    @Test
    void should_get_repair_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToRepair(any())).thenReturn(true);
        //when
        repairService.getRepairById(EXAMPLE_ID);
        //then
        verify(vehicleRepairRepository, times(1)).findRepairById(any());
    }

    @Test
    void should_get_repairs_by_vehicle() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any())).thenReturn(true);
        //when
        repairService.getRepairsByVehicle(EXAMPLE_ID);
        //then
        verify(vehicleRepairRepository, times(1)).findAllRepairsByVehicleId(any());

    }

    @Test
    void should_get_repairs_by_user() {
        //when
        repairService.getRepairsByUser();
        //then
        verify(vehicleRepairRepository, times(1)).findAllRepairByFromUserVehicle(any());
    }

    @Test
    void should_delete_vehicle_repair_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToRepair(any())).thenReturn(true);
        //when
        repairService.deleteRepairById(EXAMPLE_ID);
        //then
        verify(vehicleRepairRepository, times(1)).deleteById(any());
    }

}