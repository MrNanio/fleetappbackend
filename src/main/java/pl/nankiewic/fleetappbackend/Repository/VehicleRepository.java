package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Iterable<Vehicle> findVehiclesByUser(User user);
    Optional<Vehicle> findById(Long id);
    boolean existsById(Long id);
    boolean existsByUser(User user);



}
