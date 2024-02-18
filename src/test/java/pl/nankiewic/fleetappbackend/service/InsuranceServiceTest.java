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
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

class InsuranceServiceTest {

    @Mock
    CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    @Mock
    VehicleInsuranceRepository vehicleInsuranceRepository;
    @Mock
    InsuranceMapper insuranceMapper;

    InsuranceService insuranceService;

    private static final Long EXAMPLE_ID = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        insuranceService = new InsuranceService(
                checkExistAndPermissionComponent,
                vehicleInsuranceRepository,
                insuranceMapper);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        CustomUserDetails principal = CustomUserDetails.builder().id(1L).build();
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void should_create_vehicle_insurance() {
        //given
        when(insuranceMapper.insuranceDtoToVehicleInsuranceEntity(any())).thenReturn(VehicleInsurance.builder().build());
        when(checkExistAndPermissionComponent.accessToVehicle(any())).thenReturn(true);
        //when
        insuranceService.createVehicleInsurance(InsuranceRequestDTO.builder().build());
        //then
        verify(vehicleInsuranceRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_insurance() {
        //given
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder().build();
        when(checkExistAndPermissionComponent.accessToVehicle(any())).thenReturn(true);
        when(vehicleInsuranceRepository.findById(any())).thenReturn(Optional.of(vehicleInsurance));
        //when
        insuranceService.updateVehicleInsurance(InsuranceRequestDTO.builder().build());
        //then
        verify(vehicleInsuranceRepository, times(1)).save(any());
    }

    @Test
    void should_get_insurance_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToInsurance(any())).thenReturn(true);
        //when
        insuranceService.getInsuranceById(EXAMPLE_ID);
        //then
        verify(vehicleInsuranceRepository, times(1)).findInsuranceById(any());
    }

    @Test
    void should_get_insurance_by_user() {
        insuranceService.getInsurancesByUser();
        verify(vehicleInsuranceRepository, times(1)).findAllInsuranceByUsersVehicle(any());
    }

    @Test
    void should_get_insurance_by_vehicle() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any())).thenReturn(true);
        //when
        insuranceService.getInsurancesByVehicle(EXAMPLE_ID);
        //then
        verify(vehicleInsuranceRepository, times(1)).findAllInsuranceByVehicle(any());
    }

//    @Test
//    void should_get_insurance_type() {
//        insuranceService.getInsuranceTypes();
//        verify(insuranceTypeRepository, times(1)).findInsurancesTypes();
//    }

    @Test
    void should_delete_insurance_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToInsurance(any())).thenReturn(true);
        //when
        insuranceService.deleteInsuranceById(EXAMPLE_ID);
        //then
        verify(vehicleInsuranceRepository, times(1)).deleteById(any());
    }

}