package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.repair.RepairView;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleRepair;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRepairRepository extends JpaRepository<VehicleRepair, Long> {

    @Query(value = "SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "r.title as title, " +
            "r.repairDate as repairDate, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "WHERE v.id = :vehicleId")
    List<RepairView> findAllRepairsByVehicleId(Long vehicleId);

    @Query(value = "SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "r.title as title, " +
            "r.repairDate as repairDate, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "WHERE r.id = :repairId")
    RepairView findRepairById(Long repairId);

    @Query(value = "SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "r.title as title, " +
            "r.repairDate as repairDate, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.email=?1")
    List<RepairView> findAllRepairByFromUserVehicle(Long userId);

    List<VehicleRepair> findAllByVehicleAndRepairDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId and (r.repairDate between :begin and :end)")
    Float sumOfRepair(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRepair r " +
            "WHERE r.vehicle=?1 and (r.repairDate between ?2 and ?3)")
    Float vehicleSumCostOfRepair(Vehicle vehicle, LocalDate begin, LocalDate end);
}
