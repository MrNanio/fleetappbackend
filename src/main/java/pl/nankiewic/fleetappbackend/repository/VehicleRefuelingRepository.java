package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.DataDTO;
import pl.nankiewic.fleetappbackend.dto.DataFuelCostUserDTO;
import pl.nankiewic.fleetappbackend.dto.DataRefuelingDTO;

import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRefuelingRepository extends JpaRepository<VehicleRefueling, Long> {

    @Query(value = "SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "u.id as userId, " +
            "r.refuelingDate as refuelingDate, " +
            "r.litre as litre, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN r.user u " +
            "WHERE r.id = :id")
    RefuelingDTO findVehicleRefuelingById(Long id);

    @Query(value = "SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "u.id as userId, " +
            "r.refuelingDate as refuelingDate, " +
            "r.litre as litre, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN r.user u " +
            "WHERE v.id = :vehicleId")
    List<RefuelingDTO> findRefuelingListByVehicle(Long vehicleId);

    @Query(value = "SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "u.id as userId, " +
            "r.refuelingDate as refuelingDate, " +
            "r.litre as litre, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId")
    List<RefuelingDTO> findRefuelingListByUsersVehicle(Long userId);

    @Query(value = "SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "u.id as userId, " +
            "r.refuelingDate as refuelingDate, " +
            "r.litre as litre, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN r.user u " +
            "WHERE u.id = :userId")
    List<RefuelingDTO> findRefuelingListByUser(Long userId);

    @Query(value = "SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "u.id as userId, " +
            "r.refuelingDate as refuelingDate, " +
            "r.litre as litre, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN r.user u " +
            "WHERE v.id = :vehicleId AND u.id = :userId")
    List<RefuelingDTO> findAllByVehicleAndUser(Long vehicleId, Long userId);

    List<VehicleRefueling> findAllByVehicleIsAndRefuelingDateIsBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    List<VehicleRefueling> findAllByUserIsAndRefuelingDateIsBetween(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId and (r.refuelingDate between :begin and :end) ")
    Float sumOfRefueling(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataDTO(r.vehicle, SUM(r.cost)) " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId and (r.refuelingDate between :begin and :end) " +
            "GROUP BY r.vehicle")
    List<DataDTO> sumOfRefuelingByVehicle(Long userId, LocalDate begin, LocalDate end);

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
            "WHERE r.user.id=?1 and (r.refuelingDate between ?2 and ?3) " +
            "GROUP BY r.vehicle")
    List<DataFuelCostUserDTO> fuelCostByVehicleAndUser(Long userId, LocalDate begin, LocalDate end);
}
