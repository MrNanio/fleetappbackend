package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;

import java.util.List;

@Repository
public interface CurrentVehicleUserRepository extends JpaRepository<CurrentVehicleUser, Long> {
    Iterable<CurrentVehicleUser> findCurrentVehicleUsersByVehicleIs(Vehicle vehicle);

    List<CurrentVehicleUser> findCurrentVehicleUsersByUserIs(User user);

    CurrentVehicleUser findByVehicle(Vehicle vehicle);

    boolean existsByVehicle(Vehicle vehicle);
}
