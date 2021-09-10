package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.InsuranceRequestDTO;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.Mapper.InsuranceMapper;
import pl.nankiewic.fleetappbackend.Mapper.InsuranceTypeMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InsuranceServiceTest {
    @Mock
    VehicleInsuranceRepository vehicleInsuranceRepository;
    @Mock
    InsuranceTypeRepository insuranceTypeRepository;
    @Mock
    InsuranceMapper insuranceMapper;
    @Mock
    InsuranceTypeMapper insuranceTypeMapper;

    InsuranceService insuranceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        insuranceService = new InsuranceService(
                vehicleInsuranceRepository,
                insuranceTypeRepository,
                insuranceMapper,
                insuranceTypeMapper);
    }

    @Test
    void should_create_vehicle_insurance() {
        //given
        InsuranceRequestDTO insuranceRequestDTO = InsuranceRequestDTO.builder()
                .id(1L)
                .insuranceType("NNW")
                .cost(BigDecimal.valueOf(234.4))
                .vehicleId(1L)
                .description("hghghg")
                .expirationDate(LocalDate.now())
                .policyNumber("hghghjgbhjbjh")
                .build();
        when(insuranceMapper.insuranceDtoToVehicleInsuranceEntity(any())).thenReturn(VehicleInsurance.builder().build());
        //when
        insuranceService.createVehicleInsurance(insuranceRequestDTO);
        //then
        verify(vehicleInsuranceRepository, times(1)).save(any());

    }

    @Test
    void should_update_vehicle_insurance() {
        //given
        InsuranceRequestDTO insuranceRequestDTO = InsuranceRequestDTO.builder()
                .id(1L)
                .insuranceType("NNW")
                .cost(BigDecimal.valueOf(234.4))
                .vehicleId(1L)
                .description("hghghg")
                .expirationDate(LocalDate.now())
                .policyNumber("hghghjgbhjbjh")
                .build();
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder().build();
        when(vehicleInsuranceRepository.findById(any())).thenReturn(Optional.of(vehicleInsurance));
        //when
        insuranceService.updateVehicleInsurance(insuranceRequestDTO);
        //then
        verify(vehicleInsuranceRepository, times(1)).save(any());
    }

    @Test
    void should_get_insurance_by_id() {
        insuranceService.getInsuranceById(1L);
        verify(vehicleInsuranceRepository, times(1)).findInsuranceById(any());
    }

    @Test
    void should_get_insurance_by_user() {
        insuranceService.getInsurancesByUser(any());
        verify(vehicleInsuranceRepository, times(1)).findAllInsuranceByUsersVehicle(any());
    }

    @Test
    void should_get_insurance_by_vehicle() {
        insuranceService.getInsurancesByVehicle(1L);
        verify(vehicleInsuranceRepository, times(1)).findAllInsuranceByVehicle(any());
    }

    @Test
    void should_get_insurance_type() {
        insuranceService.getInsuranceTypes();
        verify(insuranceTypeRepository, times(1)).findAll();

    }

    @Test
    void should_delete_insurance_by_id() {
        insuranceService.deleteInsuranceById(1L);
        verify(vehicleInsuranceRepository, times(1)).deleteById(any());

    }

}