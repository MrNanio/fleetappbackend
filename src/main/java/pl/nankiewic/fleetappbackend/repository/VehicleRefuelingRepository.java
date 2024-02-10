package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.DataDTO;
import pl.nankiewic.fleetappbackend.dto.DataFuelCostUserDTO;
import pl.nankiewic.fleetappbackend.dto.DataRefuelingDTO;

import pl.nankiewic.fleetappbackend.dto.RefuelingDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRefuelingRepository extends JpaRepository<VehicleRefueling, Long> {

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.RefuelingDTO(" +
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

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.id=?1")
    List<RefuelingDTO> findRefuelingListByVehicle(Long vehicleId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.user.email=?1")
    List<RefuelingDTO> findRefuelingListByUsersVehicle(String email);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.user.email=?1")
    List<RefuelingDTO> findRefuelingListByUser(String email);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.RefuelingDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.user.id, " +
            "r.refuelingDate, " +
            "r.litre, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.id=?1 AND r.user.id=?2 ")
    List<RefuelingDTO> findAllByVehicleAndUser(Long vehicleId, Long userId);

    List<VehicleRefueling> findAllByVehicleIsAndRefuelingDateIsBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    List<VehicleRefueling> findAllByUserIsAndRefuelingDateIsBetween(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.user=?1 and (r.refuelingDate between ?2 and ?3) ")
    Float sumOfRefueling(User user, LocalDate begin, LocalDate end);

    @Query("SELECT  new pl.nankiewic.fleetappbackend.dto.DataDTO(r.vehicle, SUM(r.cost)) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle.user=?1 and (r.refuelingDate between ?2 and ?3) " +
            "GROUP BY r.vehicle")
    List<DataDTO> sumOfRefuelingByVehicle(User user, LocalDate begin, LocalDate end);

    @Query("SELECT  new pl.nankiewic.fleetappbackend.dto.DataRefuelingDTO(r.cost, r.refuelingDate) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle=?1 and (r.refuelingDate between ?2 and ?3) " +
            "ORDER BY r.refuelingDate ASC")
    List<DataRefuelingDTO> refuelingByVehicle(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRefueling r " +
            "WHERE r.vehicle=?1 and (r.refuelingDate between ?2 and ?3)")
    Float vehicleSumCostOfRefueling(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataFuelCostUserDTO(SUM(r.cost), r.vehicle) " +
            "FROM VehicleRefueling r " +
            "WHERE r.user=?1 and (r.refuelingDate between ?2 and ?3) " +
            "GROUP BY r.vehicle")
    List<DataFuelCostUserDTO> fuelCostByVehicleAndUser(User user, LocalDate begin, LocalDate end);
}
