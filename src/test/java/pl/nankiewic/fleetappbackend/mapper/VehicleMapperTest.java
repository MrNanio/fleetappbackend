package pl.nankiewic.fleetappbackend.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.enums.FuelType;
import pl.nankiewic.fleetappbackend.entity.enums.VehicleMake;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.nankiewic.fleetappbackend.entity.enums.VehicleStatus.ACTIVE;

class VehicleMapperTest {

    @InjectMocks
    private final VehicleMapper vehicleMapper = Mappers.getMapper(VehicleMapper.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_map_vehicle_request_response_to_vehicle_entity() {
        //given
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .make(VehicleMake.FORD)
                .model("FOCUS")
                .year("2010")
                .color("RED")
                .mileage("202100")
                .vinNumber("")
                .vehicleRegistrationNumber("HGTFRDE4356673287")
                .fuelType(FuelType.PB95)
                .averageFuelConsumption(BigDecimal.valueOf(10.1))
                .cityFuelConsumption(BigDecimal.valueOf(8.9))
                .countryFuelConsumption(BigDecimal.valueOf(8.9))
                .vehicleStatus(ACTIVE)
                .build();
        //when
        Vehicle vehicle = vehicleMapper.vehicleDTOtoVehicle(vehicleDTO);
        //then
        assertThat(vehicle.getColor()).isEqualTo(vehicleDTO.getColor());
        assertThat(vehicle.getVehicleStatus()).isEqualTo(ACTIVE);
        assertThat(vehicle.getFuelType()).isEqualTo(FuelType.PB95);
        assertThat(vehicle.getMake()).isEqualTo(VehicleMake.FORD);
    }

}