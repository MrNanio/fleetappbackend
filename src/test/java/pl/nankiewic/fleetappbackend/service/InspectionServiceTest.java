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
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionModifyDTO;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionView;
import pl.nankiewic.fleetappbackend.entity.VehicleInspection;
import pl.nankiewic.fleetappbackend.mapper.InspectionMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleInspectionRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InspectionServiceTest {

    @Mock
    VehicleInspectionRepository vehicleInspectionRepository;
    @Mock
    InspectionMapper inspectionMapper;

    InspectionService inspectionService;

    private static final Long EXAMPLE_ID = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        inspectionService = new InspectionService(
                vehicleInspectionRepository,
                inspectionMapper);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        CustomUserDetails principal = CustomUserDetails.builder().id(1L).build();
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void should_create_vehicle_inspection() {
        //given
        var vehicleInspection = VehicleInspection.builder().build();
        var inspectionModifyDTO = InspectionModifyDTO.builder().build();

        when(inspectionMapper.vehicleInspectionDtoToEntity(any())).thenReturn(vehicleInspection);
        when(vehicleInspectionRepository.save(any())).thenReturn(vehicleInspection);
        when(inspectionMapper.vehicleInspectionToDto(any())).thenReturn(inspectionModifyDTO);
        //when
        inspectionService.createInspection(inspectionModifyDTO);
        //then
        verify(vehicleInspectionRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_inspection() {
        //given
        var vehicleInspection = VehicleInspection.builder().build();
        var inspectionModifyDTO = InspectionModifyDTO.builder().build();

        when(vehicleInspectionRepository.findById(any())).thenReturn(Optional.of(vehicleInspection));
        when(inspectionMapper.updateVehicleInspectionFromDto(any(), any())).thenReturn(vehicleInspection);
        when(vehicleInspectionRepository.save(any())).thenReturn(vehicleInspection);
        when(inspectionMapper.vehicleInspectionToDto(any())).thenReturn(inspectionModifyDTO);
        //when
        inspectionService.updateInspection(inspectionModifyDTO);
        //then
        verify(vehicleInspectionRepository, times(1)).save(any());
    }

    @Test
    void should_get_inspection_by_id() {
        //given
        when(vehicleInspectionRepository.findInspectionById(any())).thenReturn(Optional.of(Mockito.mock(InspectionView.class)));
        //when
        inspectionService.getInspectionById(EXAMPLE_ID);
        //then
        verify(vehicleInspectionRepository, times(1)).findInspectionById(any());
    }

    @Test
    void should_get_inspection_by_user() {
        //when
        inspectionService.getInspectionsByUser();
        //then
        verify(vehicleInspectionRepository, times(1)).findInspectionViewsByUserId(any());
    }

    @Test
    void should_get_inspection_by_vehicle() {
        //when
        inspectionService.getInspectionByVehicle(EXAMPLE_ID);
        //then
        verify(vehicleInspectionRepository, times(1)).findInspectionViewsByVehicleId(any());
    }

    @Test
    void should_delete_inspection_by_id() {
        //when
        inspectionService.deleteInspectionById(EXAMPLE_ID);
        //then
        verify(vehicleInspectionRepository, times(1)).deleteById(any());
    }

}