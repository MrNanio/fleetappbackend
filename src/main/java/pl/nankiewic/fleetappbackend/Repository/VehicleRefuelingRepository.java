package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;

import java.sql.Date;
import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleRefuelingRepository extends JpaRepository<VehicleRefueling, Long> {
    Iterable <VehicleRefueling> findRefuelingsByVehicle(Vehicle vehicle);
    Iterable <VehicleRefueling> findRefuelingsByVehicleIn(Iterable<Vehicle> vehicles);
    Iterable <VehicleRefueling> findRefuelingsByUser(User user);
    Iterable <VehicleRefueling> findAllByVehicleAndUser(Vehicle vehicle, User user);
    Iterable <VehicleRefueling> findAllByVehicleIsAndRefuelingDateIsBetween(Vehicle vehicle, LocalDate begin, LocalDate end);
    @Query("SELECT SUM(r.cost) FROM VehicleRefueling r WHERE r.vehicle.user=?1 and (r.refuelingDate between ?2 and ?3)")
    Float sumOfRefueling(User user, Date begin, Date end);
}
