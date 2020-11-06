package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleUseRepository extends JpaRepository<VehicleUse, Long> {

    Iterable <VehicleUse> findAllByVehicle(Vehicle vehicle);
    Iterable <VehicleUse> findAllByUser(User user);
    Iterable <VehicleUse> findAllByVehicleAndUser(Vehicle vehicle, User user);
    Iterable <VehicleUse> findAllByVehicleIsAndTripDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);
    Iterable <VehicleUse> findAllByUserIsAndTripDateBetween(User user, LocalDate begin, LocalDate end);
}
