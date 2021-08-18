package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.DTO.DataDTO;
import pl.nankiewic.fleetappbackend.DTO.DataFuelCostUserDTO;
import pl.nankiewic.fleetappbackend.DTO.DataRefuelingDTO;

import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;

import java.time.LocalDate;

@Repository
public interface VehicleRefuelingRepository extends JpaRepository<VehicleRefueling, Long> {
    Iterable<VehicleRefueling> findRefuelingsByVehicle(Vehicle vehicle);

    Iterable<VehicleRefueling> findRefuelingsByVehicleIn(Iterable<Vehicle> vehicles);

    Iterable<VehicleRefueling> findRefuelingsByUser(User user);

    Iterable<VehicleRefueling> findAllByVehicleAndUser(Vehicle vehicle, User user);

    Iterable<VehicleRefueling> findAllByVehicleIsAndRefuelingDateIsBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    Iterable<VehicleRefueling> findAllByUserIsAndRefuelingDateIsBetween(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.user=?1 and (r.refuelingDate between ?2 and ?3) ")
    Float sumOfRefueling(User user, LocalDate begin, LocalDate end);

    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataDTO(r.vehicle, SUM(r.cost)) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.user=?1 and (r.refuelingDate between ?2 and ?3) " +
            "GROUP BY r.vehicle")
    Iterable<DataDTO> sumOfRefuelingByVehicle(User user, LocalDate begin, LocalDate end);

    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataRefuelingDTO(r.cost, r.refuelingDate) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle=?1 and (r.refuelingDate between ?2 and ?3) " +
            "ORDER BY r.refuelingDate ASC")
    Iterable<DataRefuelingDTO> refuelingByVehicle(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle=?1 and (r.refuelingDate between ?2 and ?3)")
    Float vehicleSumCostOfRefueling(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataFuelCostUserDTO(SUM(r.cost), r.vehicle) " +
            "FROM VehicleRefueling r " +
            "WHERE r.user=?1 and (r.refuelingDate between ?2 and ?3) " +
            "GROUP BY r.vehicle")
    Iterable<DataFuelCostUserDTO> fuelCostByVehicleAndUser(User user, LocalDate begin, LocalDate end);
}
