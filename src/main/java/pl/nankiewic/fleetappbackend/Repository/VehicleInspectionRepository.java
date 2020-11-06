package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInspection;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleInspectionRepository extends JpaRepository<VehicleInspection, Long> {
    Iterable <VehicleInspection> findAllByVehicleIn(Iterable<Vehicle> vehicle);
    Iterable <VehicleInspection> findAllByVehicle(Vehicle vehicle);
    Iterable <VehicleInspection> findAllByVehicleAndInspectionDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);
}
