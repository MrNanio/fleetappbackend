package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.DTO.InsuranceDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;

import java.time.LocalDate;

@Repository
public interface VehicleInsuranceRepository extends JpaRepository<VehicleInsurance, Long> {

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.InsuranceDTO(" +
            "i.id, " +
            "i.vehicle.id, " +
            "i.effectiveDate, " +
            "i.expirationDate, " +
            "i.cost, " +
            "i.policyNumber, " +
            "i.insuranceType.insuranceType, " +
            "i.description) " +
            "FROM VehicleInsurance i " +
            "WHERE i.id=?1")
    InsuranceDTO findInsuranceById(Long id);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.InsuranceDTO(" +
            "i.id, " +
            "i.vehicle.id, " +
            "i.effectiveDate, " +
            "i.expirationDate, " +
            "i.cost, " +
            "i.policyNumber, " +
            "i.insuranceType.insuranceType, " +
            "i.description) " +
            "FROM VehicleInsurance i " +
            "WHERE i.vehicle.user.email=?1")
    Iterable<InsuranceDTO> findAllInsuranceByUsersVehicle(String email);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.InsuranceDTO(" +
            "i.id, " +
            "i.vehicle.id, " +
            "i.effectiveDate, " +
            "i.expirationDate, " +
            "i.cost, " +
            "i.policyNumber, " +
            "i.insuranceType.insuranceType, " +
            "i.description) " +
            "FROM VehicleInsurance i " +
            "WHERE i.vehicle.id=?1")
    Iterable<InsuranceDTO> findAllInsuranceByVehicle(Long vehicleId);

    Iterable<VehicleInsurance> findAllByVehicleAndEffectiveDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInsurance i " +
            "WHERE (i.vehicle.user=?1 )and (i.effectiveDate between ?2 and ?3)")
    Float sumOfInsurance(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInsurance i " +
            "WHERE (i.vehicle=?1 )and (i.effectiveDate between ?2 and ?3)")
    Float vehicleSumCostOfInsurance(Vehicle vehicle, LocalDate begin, LocalDate end);
}
