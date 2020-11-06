package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleInsuranceRepository extends JpaRepository<VehicleInsurance, Long> {

    Iterable <VehicleInsurance> findAllByVehicleIn(Iterable<Vehicle> vehicle);
    Iterable <VehicleInsurance> findAllByVehicle(Vehicle vehicle);
    Iterable <VehicleInsurance> findAllByVehicleAndEffectiveDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);
}
