package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.Refueling;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;

@CrossOrigin(origins = "http://localhost:4200")
public interface RefuelingRepository extends JpaRepository<Refueling, Long> {
    Iterable <Refueling> findRefuelingsByVehicle(Vehicle vehicle);
    Iterable <Refueling> findRefuelingsByVehicleIn(Iterable<Vehicle> vehicles);
    Iterable <Refueling> findRefuelingsByUser(User user);
    Iterable <Refueling> findAllByVehicleAndUser(Vehicle vehicle, User user);
}
