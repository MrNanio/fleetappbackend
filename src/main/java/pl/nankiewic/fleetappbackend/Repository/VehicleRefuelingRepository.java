package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.DTO.DataDTO;
import pl.nankiewic.fleetappbackend.DTO.DataFuelCostUserDTO;
import pl.nankiewic.fleetappbackend.DTO.DataRefuelingDTO;

import pl.nankiewic.fleetappbackend.DTO.RefuelingDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;

import java.time.LocalDate;

@Repository
public interface VehicleRefuelingRepository extends JpaRepository<VehicleRefueling, Long> {

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.id=?1")
    RefuelingDTO findVehicleRefuelingById(Long id);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.id=?1")
    Iterable<RefuelingDTO> findRefuelingListByVehicle(Long vehicleId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.user.email=?1")
    Iterable<RefuelingDTO> findRefuelingListByUsersVehicle(String email);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.user.email=?1")
    Iterable<RefuelingDTO> findRefuelingListByUser(String email);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.id=?1 AND r.user.id=?2 ")
    Iterable<RefuelingDTO> findAllByVehicleAndUser(Long vehicleId, Long userId);

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
