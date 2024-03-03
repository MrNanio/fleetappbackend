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
import pl.nankiewic.fleetappbackend.dto.repair.RepairModifyDTO;
import pl.nankiewic.fleetappbackend.dto.repair.RepairView;
import pl.nankiewic.fleetappbackend.entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.mapper.RepairMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRepairRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RepairServiceTest {

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
        var repairModifyDTO = RepairModifyDTO.builder().build();
        var vehicleRepair = VehicleRepair.builder().build();

        when(repairMapper.repairDtoToVehicleRepairEntity(any())).thenReturn(vehicleRepair);
        when(vehicleRepairRepository.save(any())).thenReturn(vehicleRepair);
        when(repairMapper.vehicleRepairToRepairModifyDTO(any())).thenReturn(repairModifyDTO);
        //when
        repairService.createVehicleRepair(repairModifyDTO);
        //then
        verify(vehicleRepairRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_repair() {
        //given
        var repairModifyDTO = RepairModifyDTO.builder().build();
        var vehicleRepair = VehicleRepair.builder().build();

        when(vehicleRepairRepository.findById(any())).thenReturn(Optional.of(vehicleRepair));
        when(repairMapper.updateVehicleRepairFromDto(any(), any())).thenReturn(vehicleRepair);
        when(vehicleRepairRepository.save(any())).thenReturn(vehicleRepair);
        when(repairMapper.vehicleRepairToRepairModifyDTO(any())).thenReturn(repairModifyDTO);
        //when
        repairService.updateVehicleRepair(repairModifyDTO);
        //then
        verify(vehicleRepairRepository, times(1)).save(any());
    }

    @Test
    void should_get_repair_by_id() {
        //given
        when(vehicleRepairRepository.findRepairViewById(any())).thenReturn(Optional.of(Mockito.mock(RepairView.class)));
        //when
        repairService.getRepairViewById(EXAMPLE_ID);
        //then
        verify(vehicleRepairRepository, times(1)).findRepairViewById(any());
    }

    @Test
    void should_get_repairs_by_vehicle() {
        //when
        repairService.getRepairViewsByVehicleId(EXAMPLE_ID);
        //then
        verify(vehicleRepairRepository, times(1)).findRepairViewsByVehicleId(any());
    }

    @Test
    void should_get_repairs_by_user() {
        //when
        repairService.getRepairsByUser();
        //then
        verify(vehicleRepairRepository, times(1)).findRepairViewsByUserId(any());
    }

    @Test
    void should_delete_vehicle_repair_by_id() {
        //when
        repairService.deleteRepairById(EXAMPLE_ID);
        //then
        verify(vehicleRepairRepository, times(1)).deleteById(any());
    }

}