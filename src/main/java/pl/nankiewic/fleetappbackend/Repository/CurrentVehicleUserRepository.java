package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;

@CrossOrigin(origins = "http://localhost:4200")
public interface CurrentVehicleUserRepository extends JpaRepository<CurrentVehicleUser, Long> {
    Iterable <CurrentVehicleUser> findCurrentVehicleUsersByVehicleIs(Vehicle vehicle);
    Iterable <CurrentVehicleUser> findCurrentVehicleUsersByUserIs(User user);
    boolean existsByVehicle(Vehicle vehicle);
    CurrentVehicleUser findByVehicle(Vehicle vehicle);
}
