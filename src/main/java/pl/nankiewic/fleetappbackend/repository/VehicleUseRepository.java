package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.*;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleUse;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleUseRepository extends JpaRepository<VehicleUse, Long> {

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.UseDTO(" +
            "u.id, " +
            "u.vehicle.id," +
            "u.user.id, " +
            "u.trip, " +
            "u.tripDate, " +
            "u.tripType, " +
            "u.description) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.id = ?1")
    List<UseDTO> findAllByVehicleId(Long vehicleId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.UseDTO(" +
            "u.id, " +
            "u.vehicle.id," +
            "u.user.id, " +
            "u.trip, " +
            "u.tripDate, " +
            "u.tripType, " +
            "u.description) " +
            "FROM VehicleUse u " +
            "WHERE u.user.id = ?1")
    List<UseDTO> findAllByUserId(Long userId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.UseDTO(" +
            "u.id, " +
            "u.vehicle.id," +
            "u.user.id, " +
            "u.trip, " +
            "u.tripDate, " +
            "u.tripType, " +
            "u.description) " +
            "FROM VehicleUse u " +
            "WHERE u.id = ?1")
    UseDTO findByUseId(Long useId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.UseDTO(" +
            "u.id, " +
            "u.vehicle.id," +
            "u.user.id, " +
            "u.trip, " +
            "u.tripDate, " +
            "u.tripType, " +
            "u.description) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.id = ?1 AND u.user.id = ?2")
    List<UseDTO> findAllByVehicleAndUser(Long vehicleId, Long userId);

    List<VehicleUse> findAllByVehicleIsAndTripDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    List<VehicleUse> findAllByUserIsAndTripDateBetween(User user, LocalDate tripDate, LocalDate tripDate2);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataUseDTO(u.vehicle, SUM(u.trip)) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.user=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    List<DataUseDTO> sumOfRefuelingByVehicle(User user, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataTripDTO(u.trip, u.tripDate) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle=?1 and (u.tripDate between ?2 and ?3) " +
            "ORDER BY u.tripDate ASC")
    List<DataTripDTO> tripByVehicleAndData(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataUseTypeDTO(u.tripType, SUM(u.trip)) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.tripType")
    List<DataUseTypeDTO> tripByVehicleAndDataAndTripType(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataTripUserDTO(u.vehicle, SUM(u.trip)) " +
            "FROM VehicleUse u " +
            "WHERE u.user=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    List<DataTripUserDTO> tripByVehicleAndDataAndUser(User user, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataTripUserDTO(u.vehicle, COUNT(u.id)) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.user=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    List<DataTripUserDTO> numberOfUsesByVehicleAndDataAndUser(User user, LocalDate begin, LocalDate end);
}
