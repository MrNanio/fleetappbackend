package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceView;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleInsuranceReportView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleInsuranceRepository extends JpaRepository<VehicleInsurance, Long> {

    @Query(value = "SELECT i.id as id, " +
            "v.id as vehicleId, " +
            "i.effectiveDate as effectiveDate, " +
            "i.expirationDate as expirationDate, " +
            "i.cost as cost, " +
            "i.policyNumber as policyNumber, " +
            "i.insuranceType as insuranceType, " +
            "i.description as description " +
            "FROM VehicleInsurance i " +
            "JOIN i.vehicle v " +
            "WHERE i.id = :id")
    Optional<InsuranceView> findInsuranceViewById(Long id);

    @Query(value = "SELECT i.id as id, " +
            "v.id as vehicleId, " +
            "i.effectiveDate as effectiveDate, " +
            "i.expirationDate as expirationDate, " +
            "i.cost as cost, " +
            "i.policyNumber as policyNumber, " +
            "i.insuranceType as insuranceType, " +
            "i.description as description " +
            "FROM VehicleInsurance i " +
            "JOIN i.vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId")
    List<InsuranceView> findInsuranceViewsByUserId(Long userId);

    @Query(value = "SELECT i.id as id, " +
            "v.id as vehicleId, " +
            "i.effectiveDate as effectiveDate, " +
            "i.expirationDate as expirationDate, " +
            "i.cost as cost, " +
            "i.policyNumber as policyNumber, " +
            "i.insuranceType as insuranceType, " +
            "i.description as description " +
            "FROM VehicleInsurance i " +
            "JOIN i.vehicle v " +
            "WHERE v.id = :vehicleId")
    List<InsuranceView> findInsuranceViewsByVehicleId(Long vehicleId);

    @Query(value = "SELECT i.id as id, " +
            "v.id as vehicleId, " +
            "i.expirationDate as expirationDate, " +
            "i.effectiveDate as effectiveDate, " +
            "i.cost as cost, " +
            "i.description as description, " +
            "i.insuranceType as insuranceType, " +
            "u.email as createdBy " +
            "FROM VehicleInsurance i " +
            "JOIN i.vehicle v " +
            "JOIN v.user u " +
            "WHERE i.effectiveDate BETWEEN :#{#param.startDate} AND :#{#param.endDate} " +
            "AND (:#{#param.userId} IS NULL OR :#{#param.userId} = u.id) " +
            "AND (:#{#param.vehicleId} IS NULL OR :#{#param.vehicleId} = v.id)")
    List<VehicleInsuranceReportView> findVehicleInsuranceReportViewsByParam(ReportViewFilterParam param);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInsurance i " +
            "JOIN i.vehicle v " +
            "JOIN v.user u " +
            "WHERE (u.id = :userId) and (i.effectiveDate between :begin and :end)")
    BigDecimal findSummaryCostByVehicleOwner(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInsurance i " +
            "JOIN i.vehicle v " +
            "WHERE (v.id = :vehicleId) and (i.effectiveDate between :begin and :end)")
    BigDecimal findSummaryCostByVehicleId(Long vehicleId, LocalDate begin, LocalDate end);

    @Query("SELECT " +
            "CASE WHEN COUNT(i) > 0 " +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM VehicleInsurance i " +
            "JOIN i.vehicle v " +
            "JOIN v.user u " +
            "where u.id = :userId and i.id = :insuranceId")
    boolean existsByVehicleOwnerIdAndInsuranceId(Long userId, Long insuranceId);

}