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
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceModifyDTO;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceView;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

class InsuranceServiceTest {

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
        var vehicleInsurance = VehicleInsurance.builder().build();
        var insuranceModifyDTO = InsuranceModifyDTO.builder().build();

        when(vehicleInsuranceRepository.findById(any())).thenReturn(Optional.of(vehicleInsurance));
        when(insuranceMapper.insuranceDtoToVehicleInsuranceEntity(any())).thenReturn(VehicleInsurance.builder().build());
        when(vehicleInsuranceRepository.save(any())).thenReturn(vehicleInsurance);
        when(insuranceMapper.insuranceToInsuranceModifyDTO(any())).thenReturn(insuranceModifyDTO);

        //when
        insuranceService.createVehicleInsurance(insuranceModifyDTO);
        //then
        verify(vehicleInsuranceRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_insurance() {
        //given
        var vehicleInsurance = VehicleInsurance.builder().build();
        var insuranceModifyDTO = InsuranceModifyDTO.builder().build();

        when(vehicleInsuranceRepository.findById(any())).thenReturn(Optional.of(vehicleInsurance));
        when(insuranceMapper.updateVehicleInsuranceFromDto(any(), any())).thenReturn(vehicleInsurance);
        when(vehicleInsuranceRepository.save(any())).thenReturn(vehicleInsurance);
        when(insuranceMapper.insuranceToInsuranceModifyDTO(any())).thenReturn(insuranceModifyDTO);
        //when
        insuranceService.updateVehicleInsurance(insuranceModifyDTO);
        //then
        verify(vehicleInsuranceRepository, times(1)).save(any());
    }

    @Test
    void should_get_insurance_by_id() {
        //given
        when(vehicleInsuranceRepository.findInsuranceViewById(any())).thenReturn(Optional.of(Mockito.mock(InsuranceView.class)));
        //when
        insuranceService.getInsuranceViewByInsuranceId(EXAMPLE_ID);
        //then
        verify(vehicleInsuranceRepository, times(1)).findInsuranceViewById(any());
    }

    @Test
    void should_get_insurance_by_user() {
        insuranceService.getInsurancesByUser();
        verify(vehicleInsuranceRepository, times(1)).findAllInsuranceByUsersVehicle(any());
    }

    @Test
    void should_get_insurance_by_vehicle() {
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
        //when
        insuranceService.deleteInsuranceById(EXAMPLE_ID);
        //then
        verify(vehicleInsuranceRepository, times(1)).deleteById(any());
    }

}