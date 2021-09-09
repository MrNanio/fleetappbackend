package pl.nankiewic.fleetappbackend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.nankiewic.fleetappbackend.Mapper.UseMapper;
import pl.nankiewic.fleetappbackend.Repository.*;

import static org.junit.jupiter.api.Assertions.*;

class UseServiceTest {

    @Mock
    VehicleUseRepository vehicleUseRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    UseMapper useMapper;

    UseService useService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useService = new UseService(
                vehicleUseRepository,
                vehicleRepository,
                userRepository,
                useMapper);
    }

    void should_create_vehicle_use() {

    }

    void should_update_vehicle_use() {

    }

    void should_get_use_by_vehicle() {

    }

    void should_get_use_by_id() {

    }

    void should_get_use_by_user() {

    }

    void should_get_use_by_user_and_vehicle() {

    }

    void should_delete_use_by_id() {

    }
}