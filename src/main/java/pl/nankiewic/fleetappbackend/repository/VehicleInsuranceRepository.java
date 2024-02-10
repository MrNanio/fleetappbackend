package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.insurance.InsuranceView;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;

import java.time.LocalDate;
import java.util.List;

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
            "WHERE i.id=?1")
    InsuranceView findInsuranceById(Long id);

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
            "WHERE u.email=?1")
    List<InsuranceView> findAllInsuranceByUsersVehicle(String email);

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
            "WHERE v.id=?1")
    List<InsuranceView> findAllInsuranceByVehicle(Long vehicleId);

    List<VehicleInsurance> findAllByVehicleAndEffectiveDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInsurance i " +
            "WHERE (i.vehicle.user=?1 )and (i.effectiveDate between ?2 and ?3)")
    Float sumOfInsurance(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInsurance i " +
            "WHERE (i.vehicle=?1 )and (i.effectiveDate between ?2 and ?3)")
    Float vehicleSumCostOfInsurance(Vehicle vehicle, LocalDate begin, LocalDate end);
}
