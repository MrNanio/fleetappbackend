package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleRepairRepository extends JpaRepository<VehicleRepair, Long> {
    Iterable <VehicleRepair> findAllByVehicle(Vehicle vehicle);
    Iterable <VehicleRepair> findAllByVehicleIn(Iterable<Vehicle> vehicle);
    Iterable <VehicleRepair> findAllByVehicleAndRepairDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

}
