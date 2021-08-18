package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleInsurance;

import java.sql.Date;
import java.time.LocalDate;

@Repository
public interface VehicleInsuranceRepository extends JpaRepository<VehicleInsurance, Long> {
    Iterable<VehicleInsurance> findAllByVehicleIn(Iterable<Vehicle> vehicle);

    Iterable<VehicleInsurance> findAllByVehicle(Vehicle vehicle);

    Iterable<VehicleInsurance> findAllByVehicleAndEffectiveDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInsurance i " +
            "WHERE (i.vehicle.user=?1 )and (i.effectiveDate between ?2 and ?3)")
    Float sumOfInsurance(User user, Date begin, Date end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInsurance i " +
            "WHERE (i.vehicle=?1 )and (i.effectiveDate between ?2 and ?3)")
    Float vehicleSumCostOfInsurance(Vehicle vehicle, Date begin, Date end);
}
