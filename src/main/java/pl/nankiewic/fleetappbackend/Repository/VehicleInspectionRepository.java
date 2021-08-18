package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInspection;

import java.time.LocalDate;

@Repository
public interface VehicleInspectionRepository extends JpaRepository<VehicleInspection, Long> {
    Iterable<VehicleInspection> findAllByVehicleIn(Iterable<Vehicle> vehicle);

    Iterable<VehicleInspection> findAllByVehicle(Vehicle vehicle);

    Iterable<VehicleInspection> findAllByVehicleAndInspectionDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInspection i " +
            "WHERE i.vehicle.user=?1 and (i.inspectionDate between ?2 and ?3)")
    Float sumOfInspection(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInspection i " +
            "WHERE i.vehicle=?1 and (i.inspectionDate between ?2 and ?3)")
    Float vehicleSumCostOfInspection(Vehicle vehicle, LocalDate begin, LocalDate end);
}
