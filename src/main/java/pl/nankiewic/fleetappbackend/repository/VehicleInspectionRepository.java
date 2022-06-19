package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.DTO.InspectionDTO;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleInspection;

import java.time.LocalDate;

@Repository
public interface VehicleInspectionRepository extends JpaRepository<VehicleInspection, Long> {

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.InspectionDTO(" +
            "i.id, " +
            "i.vehicle.id, " +
            "i.inspectionDate, " +
            "i.expirationDate, " +
            "i.cost, " +
            "i.description) " +
            "FROM VehicleInspection i " +
            "WHERE i.vehicle.user.email=?1")
    Iterable<InspectionDTO> findAllByVehicleIn(String email);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.InspectionDTO(" +
            "i.id, " +
            "i.vehicle.id, " +
            "i.inspectionDate, " +
            "i.expirationDate, " +
            "i.cost, " +
            "i.description) " +
            "FROM VehicleInspection i " +
            "WHERE i.id=?1")
    InspectionDTO findInspectionById(Long inspectionId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.InspectionDTO(" +
            "i.id, " +
            "i.vehicle.id, " +
            "i.inspectionDate, " +
            "i.expirationDate, " +
            "i.cost, " +
            "i.description) " +
            "FROM VehicleInspection i " +
            "WHERE i.vehicle.id=?1")
    Iterable<InspectionDTO> findAllByVehicle(Long vehicleId);

    Iterable<VehicleInspection> findAllByVehicleAndInspectionDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInspection i " +
            "WHERE i.vehicle.user=?1 and (i.inspectionDate between ?2 and ?3)")
    Float sumOfInspection(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(i.cost) " +
            "FROM VehicleInspection i " +
            "WHERE i.vehicle=?1 and (i.inspectionDate between ?2 and ?3)")
    Float vehicleSumCostOfInspection(Vehicle vehicle, LocalDate begin, LocalDate end);
}
