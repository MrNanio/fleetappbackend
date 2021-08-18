package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;

import java.sql.Date;
import java.time.LocalDate;

@Repository
public interface VehicleRepairRepository extends JpaRepository<VehicleRepair, Long> {
    Iterable<VehicleRepair> findAllByVehicle(Vehicle vehicle);

    Iterable<VehicleRepair> findAllByVehicleIn(Iterable<Vehicle> vehicle);

    Iterable<VehicleRepair> findAllByVehicleAndRepairDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRepair r " +
            "WHERE r.vehicle.user=?1 and (r.repairDate between ?2 and ?3)")
    Float sumOfRepair(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRepair r " +
            "WHERE r.vehicle=?1 and (r.repairDate between ?2 and ?3)")
    Float vehicleSumCostOfRepair(Vehicle vehicle, LocalDate begin, LocalDate end);
}
