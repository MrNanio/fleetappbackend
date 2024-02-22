package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionView;
import pl.nankiewic.fleetappbackend.entity.VehicleInspection;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleInspectionReportView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleInspectionRepository extends JpaRepository<VehicleInspection, Long> {

    @Query(value = "SELECT i.id as id, " +
            "v.id as vehicleId, " +
            "i.inspectionDate as inspectionDate, " +
            "i.expirationDate as expirationDate, " +
            "i.cost as cost, " +
            "i.description as description " +
            "FROM VehicleInspection i " +
            "JOIN i.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId")
    List<InspectionView> findAllByVehicleIn(Long userId);

    @Query(value = "SELECT i.id as id, " +
            "v.id as vehicleId, " +
            "i.inspectionDate as inspectionDate, " +
            "i.expirationDate as expirationDate, " +
            "i.cost as cost, " +
            "i.description as description " +
            "FROM VehicleInspection i " +
            "JOIN i.vehicle v " +
            "WHERE i.id = :inspectionId")
    Optional<InspectionView> findInspectionById(Long inspectionId);

    @Query(value = "SELECT i.id as id, " +
            "v.id as vehicleId, " +
            "i.inspectionDate as inspectionDate, " +
            "i.expirationDate as expirationDate, " +
            "i.cost as cost, " +
            "i.description as description " +
            "FROM VehicleInspection i " +
            "JOIN i.vehicle v " +
            "WHERE i.vehicle.id=?1")
    List<InspectionView> findAllByVehicle(Long vehicleId);

    @Query(value = "SELECT i.id as id, " +
            "v.id as vehicleId, " +
            "i.inspectionDate as inspectionDate, " +
            "i.expirationDate as expirationDate, " +
            "i.cost as cost, " +
            "i.description as description, " +
            "u.email as createdBy " +
            "FROM VehicleInspection i " +
            "JOIN i.vehicle v " +
            "JOIN v.user u " +
            "WHERE i.inspectionDate BETWEEN :#{#param.startDate} AND :#{#param.endDate} " +
            "AND (:#{#param.userId} IS NULL OR :#{#param.userId} = u.id) " +
            "AND (:#{#param.vehicleId} IS NULL OR :#{#param.vehicleId} = v.id)")
    List<VehicleInspectionReportView> findVehicleInspectionReportViewByParam(ReportViewFilterParam param);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInspection i " +
            "JOIN i.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId AND (i.inspectionDate BETWEEN :begin AND :end)")
    BigDecimal findSummaryCostByVehicleOwner(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInspection i " +
            "JOIN i.vehicle v " +
            "WHERE v.id = :vehicleId AND (i.inspectionDate BETWEEN :begin AND :end)")
    BigDecimal findSummaryCostByVehicleId(Long vehicleId, LocalDate begin, LocalDate end);

    @Query("SELECT " +
            "CASE WHEN COUNT(i) > 0 " +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM VehicleInspection i " +
            "JOIN i.vehicle v " +
            "JOIN v.user u " +
            "where u.id = :userId and i.id = :inspectionId")
    boolean existsByVehicleOwnerIdAndInsuranceId(Long userId, Long inspectionId);

}