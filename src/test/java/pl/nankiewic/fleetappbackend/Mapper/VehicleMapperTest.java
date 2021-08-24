package pl.nankiewic.fleetappbackend.Mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumFuelType;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumVehicleStatus;
import pl.nankiewic.fleetappbackend.Entity.FuelType;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleStatus;
import pl.nankiewic.fleetappbackend.Repository.FuelTypeRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleMakeRepository;
import pl.nankiewic.fleetappbackend.Repository.VehicleStatusRepository;

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
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void should_map_vehicle_request_response_to_vehicle_entity() {
        //given
        VehicleRequestResponseDTO vehicleRequestResponseDTO = new VehicleRequestResponseDTO(1L,
                "FORD",
                "FOCUS",
                "2010",
                "RED",
                "109888",
                "HGTFRDE4356673287",
                "WI11111",
                "PB95",
                BigDecimal.valueOf(10.1),
                BigDecimal.valueOf(8.9),
                BigDecimal.valueOf(7.3),
                "ACTIVE");

        when(vehicleStatusRepository.findByEnumName(any())).thenReturn(new VehicleStatus(1L, EnumVehicleStatus.ACTIVE));
        when(fuelTypeRepository.findByEnumName(any())).thenReturn(new FuelType(1L, EnumFuelType.PB95));
        //when
        Vehicle vehicle = vehicleMapper.vehicleDTOtoVehicle(vehicleRequestResponseDTO);
        vehicleMapper.vehicleValueMapToEnum(vehicleRequestResponseDTO, vehicle);
        //then
        assertThat(vehicle.getColor()).isEqualTo(vehicleRequestResponseDTO.getColor());
        assertThat(vehicle.getVehicleStatus().getVehicleStatus()).isEqualTo(EnumVehicleStatus.ACTIVE);
        assertThat(vehicle.getFuelType().getFuelType()).isEqualTo(EnumFuelType.PB95);
    }

}