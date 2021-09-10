package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.DTO.RepairDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;

import java.time.LocalDate;

@Repository
public interface VehicleRepairRepository extends JpaRepository<VehicleRepair, Long> {

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.RepairDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.title, " +
            "r.repairDate, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRepair r " +
            "WHERE r.vehicle.id=?1")
    Iterable<RepairDTO> findAllRepairsByVehicleId(Long vehicleId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.RepairDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.title, " +
            "r.repairDate, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRepair r " +
            "WHERE r.id=?1")
    RepairDTO findRepairById(Long repairId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.RepairDTO(" +
            "r.id, " +
            "r.vehicle.id, " +
            "r.title, " +
            "r.repairDate, " +
            "r.cost, " +
            "r.description) " +
            "FROM VehicleRepair r " +
            "WHERE r.vehicle.user.email=?1")
    Iterable<RepairDTO> findAllRepairByFromUserVehicle(String email);

    Iterable<VehicleRepair> findAllByVehicleAndRepairDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);


    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRepair r " +
            "WHERE r.vehicle.user=?1 and (r.repairDate between ?2 and ?3)")
    Float sumOfRepair(User user, LocalDate begin, LocalDate end);

    @Query("SELECT SUM(r.cost) " +
            "FROM VehicleRepair r " +
            "WHERE r.vehicle=?1 and (r.repairDate between ?2 and ?3)")
    Float vehicleSumCostOfRepair(Vehicle vehicle, LocalDate begin, LocalDate end);
}
