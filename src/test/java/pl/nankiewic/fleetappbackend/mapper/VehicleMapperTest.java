package pl.nankiewic.fleetappbackend.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.entity.*;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumFuelType;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumVehicleStatus;
import pl.nankiewic.fleetappbackend.repository.FuelTypeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleMakeRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleStatusRepository;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

class VehicleMapperTest {
    @Mock
    private VehicleStatusRepository vehicleStatusRepository;
    @Mock
    private FuelTypeRepository fuelTypeRepository;
    @Mock
    private VehicleMakeRepository vehicleMakeRepository;

    @InjectMocks
    private final VehicleMapper vehicleMapper = Mappers.getMapper(VehicleMapper.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_map_vehicle_request_response_to_vehicle_entity() {
        //given
        VehicleRequestResponseDTO vehicleRequestResponseDTO = VehicleRequestResponseDTO.builder()
                .make("FORD")
                .model("FOCUS")
                .year("2010")
                .color("RED")
                .mileage("202100")
                .vinNumber("")
                .vehicleRegistrationNumber("HGTFRDE4356673287")
                .fuelType("PB95")
                .averageFuelConsumption(BigDecimal.valueOf(10.1))
                .cityFuelConsumption(BigDecimal.valueOf(8.9))
                .countryFuelConsumption(BigDecimal.valueOf(8.9))
                .vehicleStatus("ACTIVE")
                .build();

        User user = User.builder().build();
        when(vehicleStatusRepository.findByEnumName(any())).thenReturn(VehicleStatus.builder().vehicleStatus(EnumVehicleStatus.ACTIVE).build());
        when(fuelTypeRepository.findByEnumName(any())).thenReturn(FuelType.builder().fuelType(EnumFuelType.PB95).build());
        when(vehicleMakeRepository.findByName(any())).thenReturn(VehicleMake.builder().name("FORD").build());
        //when
        Vehicle vehicle = vehicleMapper.vehicleDTOtoVehicle(vehicleRequestResponseDTO, user);
        //then
        assertThat(vehicle.getColor()).isEqualTo(vehicleRequestResponseDTO.getColor());
        assertThat(vehicle.getVehicleStatus().getVehicleStatus()).isEqualTo(EnumVehicleStatus.ACTIVE);
        assertThat(vehicle.getFuelType().getFuelType()).isEqualTo(EnumFuelType.PB95);
        assertThat(vehicle.getVehicleMake().getName()).isEqualTo(vehicleRequestResponseDTO.getMake());
    }

}