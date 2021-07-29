package pl.nankiewic.fleetappbackend.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class VehicleMapperTest {
    @Autowired
    private VehicleMapper vehicleMapper;

    public void should_map_vehicle_to_vehicle_dto(){
        //given
        Vehicle vehicle = null;
        //when
        VehicleDTO vehicleDTO = vehicleMapper.vehicleToVehicleDTO(vehicle);
        //then
        assertThat(vehicleDTO).isNotNull();
        assertThat(vehicleDTO.getId()).isEqualTo(1L);
    }

}