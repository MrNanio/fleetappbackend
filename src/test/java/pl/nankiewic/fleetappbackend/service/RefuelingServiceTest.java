package pl.nankiewic.fleetappbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.nankiewic.fleetappbackend.config.security.CustomUserDetails;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingModifyDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;
import pl.nankiewic.fleetappbackend.mapper.RefuelingMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleRefuelingRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class RefuelingServiceTest {

    @Mock
    VehicleRefuelingRepository vehicleRefuelingRepository;
    @Mock
    RefuelingMapper refuelingMapper;

    RefuelingService refuelingService;

    private static final Long EXAMPLE_ID = 1L;


    @BeforeEach
    public void setUp() {
        openMocks(this);
        refuelingService = new RefuelingService(
                vehicleRefuelingRepository,
                refuelingMapper);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        CustomUserDetails principal = CustomUserDetails.builder().id(1L).build();
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void should_create_vehicle_refueling() {
        //given
        var refuelingModifyDTO = RefuelingModifyDTO.builder().build();
        var vehicleRefueling = Mockito.mock(VehicleRefueling.class);

        when(refuelingMapper.refuelingDtoToVehicleRefuelingEntity(any())).thenReturn(vehicleRefueling);
        when(vehicleRefuelingRepository.save(any())).thenReturn(vehicleRefueling);
        when(refuelingMapper.refuelingToRefuelingModify(any())).thenReturn(refuelingModifyDTO);
        //when
        refuelingService.createVehicleRefueling(refuelingModifyDTO);
        //then
        verify(vehicleRefuelingRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_refueling() {
        //given
        var refuelingModifyDTO = RefuelingModifyDTO.builder().build();
        var vehicleRefueling = Mockito.mock(VehicleRefueling.class);

        when(vehicleRefuelingRepository.findById(any())).thenReturn(Optional.of(vehicleRefueling));
        when(refuelingMapper.updateVehicleRepairFromDto(any(), any())).thenReturn(vehicleRefueling);
        when(refuelingMapper.refuelingToRefuelingModify(any())).thenReturn(refuelingModifyDTO);
        when(vehicleRefuelingRepository.save(any())).thenReturn(vehicleRefueling);
        when(refuelingMapper.refuelingToRefuelingModify(any())).thenReturn(refuelingModifyDTO);
        //when
        refuelingService.updateVehicleRefueling(refuelingModifyDTO);
        //then
        verify(vehicleRefuelingRepository, times(1)).save(any());
    }

    @Test
    void should_get_refueling_by_id() {
        //when
        refuelingService.getRefuelingById(EXAMPLE_ID);
        //then
        verify(vehicleRefuelingRepository, times(1)).findRefuelingViewById(any());
    }

    @Test
    void should_get_refueling_by_vehicle() {
        //when
        refuelingService.getRefuelingByVehicle(EXAMPLE_ID);
        //then
        verify(vehicleRefuelingRepository, times(1)).findRefuelingViewsByVehicleId(any());
    }

    @Test
    void should_get_refueling_by_user() {
        //when
        refuelingService.getRefuelingByUser();
        //then
        verify(vehicleRefuelingRepository, times(1)).findRefuelingViewsByVehicleOwnerId(any());
    }

    @Test
    void should_get_refueling_by_author() {
        //when
        refuelingService.getRefuelingByAuthor();
        //then
        verify(vehicleRefuelingRepository, times(1)).findRefuelingViewsByRefuelingUserId(any());
    }

    @Test
    void should_get_refueling_by_user_and_vehicle() {
        //when
        refuelingService.getRefuelingByUserAndVehicle(EXAMPLE_ID, EXAMPLE_ID);
        //then
        verify(vehicleRefuelingRepository, times(1)).findRefuelingViewsByVehicleIdAndRefuelingUserId(any(), any());
    }

    @Test
    void should_delete_by_id() {
        //when
        refuelingService.deleteRefuelingById(EXAMPLE_ID);
        //then
        verify(vehicleRefuelingRepository, times(1)).deleteById(any());
    }

}