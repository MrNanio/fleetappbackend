package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.chart.RefuelingChartWithDateView;
import pl.nankiewic.fleetappbackend.dto.chart.RefuelingChartWithVehicleDataView;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingView;
import pl.nankiewic.fleetappbackend.entity.VehicleRefueling;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleRefuelingReportView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRefuelingRepository extends JpaRepository<VehicleRefueling, Long> {

    @Query("SELECT r.id as id, " +
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
    RefuelingView findRefuelingViewById(Long id);

    @Query("SELECT r.id as id, " +
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
    List<RefuelingView> findRefuelingViewsByVehicleId(Long vehicleId);

    @Query("SELECT r.id as id, " +
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
    List<RefuelingView> findRefuelingViewsByVehicleOwnerId(Long userId);

    @Query("SELECT r.id as id, " +
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
    List<RefuelingView> findRefuelingViewsByRefuelingUserId(Long userId);

    @Query("SELECT r.id as id, " +
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
    List<RefuelingView> findRefuelingViewsByVehicleIdAndRefuelingUserId(Long vehicleId, Long userId);

    @Query("SELECT r.id as id, " +
            "v.id as vehicleId, " +
            "r.litre as litre, " +
            "r.cost as cost, " +
            "r.refuelingDate as refuelingDate, " +
            "r.description as description, " +
            "u.email as createdBy " +
            "FROM VehicleRefueling r " +
            "JOIN r.user u " +
            "JOIN r.vehicle v " +
            "WHERE r.refuelingDate BETWEEN :#{#param.startDate} AND :#{#param.startDate} " +
            "AND (:#{#param.userId} IS NULL OR :#{#param.userId} = u.id) " +
            "AND (:#{#param.vehicleId} IS NULL OR :#{#param.vehicleId} = v.id)")
    List<VehicleRefuelingReportView> findVehicleRefuelingReportViewsByParam(ReportViewFilterParam param);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId AND (r.refuelingDate between :begin AND :end) ")
    BigDecimal findSummaryCostByVehicleOwner(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "WHERE v.id = :vehicleId AND (r.refuelingDate between :begin AND :end)")
    BigDecimal findSummaryCostByVehicleId(Long vehicleId, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) as cost, " +
            "v.vehicleRegistrationNumber as vehicleData " +
            "FROM VehicleRefueling r " +
            "JOIN r.user u " +
            "JOIN r.vehicle v " +
            "WHERE u.id = :userId and (r.refuelingDate between :begin and :end) " +
            "GROUP BY v.id")
    List<RefuelingChartWithVehicleDataView> fuelCostByVehicleAndUser(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) as cost, " +
            "v.vehicleRegistrationNumber as vehicleData " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId and (r.refuelingDate between :begin and :end) " +
            "GROUP BY v.id")
    List<RefuelingChartWithVehicleDataView> sumOfRefuelingByVehicle(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT r.cost as value, " +
            "r.refuelingDate as date " +
            "FROM VehicleRefueling r " +
            "JOIN r.vehicle v " +
            "WHERE v.id = :vehicleId AND (r.refuelingDate between :begin AND :end) " +
            "ORDER BY r.refuelingDate ASC")
    List<RefuelingChartWithDateView> refuelingByVehicle(Long vehicleId, LocalDate begin, LocalDate end);

}