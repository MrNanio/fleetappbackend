package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import pl.nankiewic.fleetappbackend.DTO.DataDTO;
import pl.nankiewic.fleetappbackend.DTO.DataFuelCostUserDTO;
import pl.nankiewic.fleetappbackend.DTO.DataRefuelingDTO;

import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;

import java.sql.Date;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleRefuelingRepository extends JpaRepository<VehicleRefueling, Long> {
    Iterable <VehicleRefueling> findRefuelingsByVehicle(Vehicle vehicle);
    Iterable <VehicleRefueling> findRefuelingsByVehicleIn(Iterable<Vehicle> vehicles);
    Iterable <VehicleRefueling> findRefuelingsByUser(User user);
    Iterable <VehicleRefueling> findAllByVehicleAndUser(Vehicle vehicle, User user);
    Iterable <VehicleRefueling> findAllByVehicleIsAndRefuelingDateIsBetween(Vehicle vehicle, Date begin, Date end);
    Iterable <VehicleRefueling> findAllByUserIsAndRefuelingDateIsBetween(User user, Date begin, Date end);
    @Query("SELECT SUM(r.cost) FROM VehicleRefueling r WHERE r.vehicle.user=?1 and (r.refuelingDate between ?2 and ?3) ")
    Float sumOfRefueling(User user, Date begin, Date end);
    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataDTO(r.vehicle, SUM(r.cost)) FROM VehicleRefueling r WHERE r.vehicle.user=?1 and (r.refuelingDate between ?2 and ?3) group by r.vehicle")
    Iterable<DataDTO> sumOfRefuelingByVehicle(User user, Date begin, Date end);
    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataRefuelingDTO(r.cost, r.refuelingDate) FROM VehicleRefueling r WHERE r.vehicle=?1 and (r.refuelingDate between ?2 and ?3)")
    Iterable<DataRefuelingDTO> refuelingByVehicle(Vehicle vehicle, Date begin, Date end);
    @Query("SELECT SUM(r.cost) FROM VehicleRefueling r WHERE r.vehicle=?1 and (r.refuelingDate between ?2 and ?3)")
    Float vehicleSumCostOfRefueling(Vehicle vehicle, Date begin, Date end);
    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataFuelCostUserDTO(SUM(r.cost), r.vehicle) FROM VehicleRefueling r WHERE r.user=?1 and (r.refuelingDate between ?2 and ?3) group by r.vehicle")
    Iterable<DataFuelCostUserDTO> fuelCostByVehicleAndUser(User user, Date begin, Date end);
}
