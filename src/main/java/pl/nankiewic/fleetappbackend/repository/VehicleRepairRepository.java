package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.repair.RepairView;
import pl.nankiewic.fleetappbackend.entity.VehicleRepair;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleRepairReportView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepairRepository extends JpaRepository<VehicleRepair, Long> {

    @Query("SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "r.title as title, " +
            "r.repairDate as repairDate, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "WHERE v.id = :vehicleId")
    List<RepairView> findAllRepairsByVehicleId(Long vehicleId);

    @Query("SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "r.title as title, " +
            "r.repairDate as repairDate, " +
            "r.cost as cost, " +
            "r.description as description " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "WHERE r.id = :repairId")
    Optional<RepairView> findRepairById(Long repairId);

    @Query("SELECT r.id as id, " +
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

    @Query("SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "r.cost as cost, " +
            "r.title as title, " +
            "r.repairDate as repairDate, " +
            "r.description as description, " +
            "u.email as createdBy " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE r.repairDate BETWEEN :#{#param.startDate} AND :#{#param.startDate} " +
            "AND (:#{#param.userId} IS NULL OR :#{#param.userId} = u.id) " +
            "AND (:#{#param.vehicleId} IS NULL OR :#{#param.vehicleId} = v.id)")
    List<VehicleRepairReportView> findVehicleRepairReportViewByParam(ReportViewFilterParam param);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId and (r.repairDate between :begin and :end)")
    BigDecimal findSummaryCostByVehicleOwner(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRepair r " +
            "JOIN r.vehicle v " +
            "WHERE v.id = :vehicleId and (r.repairDate between :begin and :end)")
    BigDecimal findSummaryCostByVehicleId(Long vehicleId, LocalDate begin, LocalDate end);

}
