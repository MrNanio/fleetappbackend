package pl.nankiewic.fleetappbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.repository.InsuranceTypeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleInsuranceRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

class InsuranceServiceTest {

    @Mock
    CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    @Mock
    VehicleInsuranceRepository vehicleInsuranceRepository;
    @Mock
    InsuranceTypeRepository insuranceTypeRepository;
    @Mock
    InsuranceMapper insuranceMapper;

    InsuranceService insuranceService;

    private static final String EXAMPLE_EMAIL_ADDRESS = "example@example.com";
    private static final Long EXAMPLE_ID = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        insuranceService = new InsuranceService(
                checkExistAndPermissionComponent,
                vehicleInsuranceRepository,
                insuranceTypeRepository,
                insuranceMapper);
    }

    @Test
    void should_create_vehicle_insurance() {
        //given
        when(insuranceMapper.insuranceDtoToVehicleInsuranceEntity(any())).thenReturn(VehicleInsurance.builder().build());
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        insuranceService.createVehicleInsurance(InsuranceRequestDTO.builder().build(), EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleInsuranceRepository, times(1)).save(any());
    }

    @Test
    void should_update_vehicle_insurance() {
        //given
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder().build();
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        when(vehicleInsuranceRepository.findById(any())).thenReturn(Optional.of(vehicleInsurance));
        //when
        insuranceService.updateVehicleInsurance(InsuranceRequestDTO.builder().build(), EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleInsuranceRepository, times(1)).save(any());
    }

    @Test
    void should_get_insurance_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToInsurance(any(), any())).thenReturn(true);
        //when
        insuranceService.getInsuranceById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleInsuranceRepository, times(1)).findInsuranceById(any());
    }

    @Test
    void should_get_insurance_by_user() {
        insuranceService.getInsurancesByUser(any());
        verify(vehicleInsuranceRepository, times(1)).findAllInsuranceByUsersVehicle(any());
    }

    @Test
    void should_get_insurance_by_vehicle() {
        //given
        when(checkExistAndPermissionComponent.accessToVehicle(any(), any())).thenReturn(true);
        //when
        insuranceService.getInsurancesByVehicle(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleInsuranceRepository, times(1)).findAllInsuranceByVehicle(any());
    }

    @Test
    void should_get_insurance_type() {
        insuranceService.getInsuranceTypes();
        verify(insuranceTypeRepository, times(1)).findInsurancesTypes();
    }

    @Test
    void should_delete_insurance_by_id() {
        //given
        when(checkExistAndPermissionComponent.accessToInsurance(any(), any())).thenReturn(true);
        //when
        insuranceService.deleteInsuranceById(EXAMPLE_ID, EXAMPLE_EMAIL_ADDRESS);
        //then
        verify(vehicleInsuranceRepository, times(1)).deleteById(any());
    }

}