package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.inspection.InspectionView;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleInspection;

import java.time.LocalDate;
import java.util.List;

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
    InspectionView findInspectionById(Long inspectionId);

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

    Iterable<VehicleInspection> findAllByVehicleAndInspectionDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInspection i " +
            "WHERE i.vehicle.user.id=?1 and (i.inspectionDate between ?2 and ?3)")
    Float sumOfInspection(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInspection i " +
            "WHERE i.vehicle=?1 and (i.inspectionDate between ?2 and ?3)")
    Float vehicleSumCostOfInspection(Vehicle vehicle, LocalDate begin, LocalDate end);
}
