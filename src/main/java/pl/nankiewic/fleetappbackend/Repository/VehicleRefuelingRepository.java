package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleRefuelingRepository extends JpaRepository<VehicleRefueling, Long> {
    Iterable <VehicleRefueling> findRefuelingsByVehicle(Vehicle vehicle);
    Iterable <VehicleRefueling> findRefuelingsByVehicleIn(Iterable<Vehicle> vehicles);
    Iterable <VehicleRefueling> findRefuelingsByUser(User user);
    Iterable <VehicleRefueling> findAllByVehicleAndUser(Vehicle vehicle, User user);
    Iterable <VehicleRefueling> findAllByVehicleIsAndRefuelingDateIsBetween(Vehicle vehicle, LocalDate begin, LocalDate end);
}
