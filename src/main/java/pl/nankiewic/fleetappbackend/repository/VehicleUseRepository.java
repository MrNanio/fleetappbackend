package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.DTO.*;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleUse;

import java.time.LocalDate;

@Repository
public interface VehicleUseRepository extends JpaRepository<VehicleUse, Long> {

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.UseDTO(" +
            "u.id, " +
            "u.vehicle.id," +
            "u.user.id, " +
            "u.trip, " +
            "u.tripDate, " +
            "u.tripType, " +
            "u.description) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.id = ?1")
    Iterable<UseDTO> findAllByVehicleId(Long vehicleId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.UseDTO(" +
            "u.id, " +
            "u.vehicle.id," +
            "u.user.id, " +
            "u.trip, " +
            "u.tripDate, " +
            "u.tripType, " +
            "u.description) " +
            "FROM VehicleUse u " +
            "WHERE u.user.id = ?1")
    Iterable<UseDTO> findAllByUserId(Long userId);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.UseDTO(" +
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

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.UseDTO(" +
            "u.id, " +
            "u.vehicle.id," +
            "u.user.id, " +
            "u.trip, " +
            "u.tripDate, " +
            "u.tripType, " +
            "u.description) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.id = ?1 AND u.user.id = ?2")
    Iterable<UseDTO> findAllByVehicleAndUser(Long vehicleId, Long userId);

    Iterable<VehicleUse> findAllByVehicleIsAndTripDateBetween(Vehicle vehicle, LocalDate begin, LocalDate end);

    Iterable<VehicleUse> findAllByUserIsAndTripDateBetween(User user, LocalDate tripDate, LocalDate tripDate2);

    @Query("SELECT new pl.nankiewic.fleetappbackend.DTO.DataUseDTO(u.vehicle, SUM(u.trip)) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.user=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    Iterable<DataUseDTO> sumOfRefuelingByVehicle(User user, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.DTO.DataTripDTO(u.trip, u.tripDate) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle=?1 and (u.tripDate between ?2 and ?3) " +
            "ORDER BY u.tripDate ASC")
    Iterable<DataTripDTO> tripByVehicleAndData(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.DTO.DataUseTypeDTO(u.tripType, SUM(u.trip)) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.tripType")
    Iterable<DataUseTypeDTO> tripByVehicleAndDataAndTripType(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.DTO.DataTripUserDTO(u.vehicle, SUM(u.trip)) " +
            "FROM VehicleUse u " +
            "WHERE u.user=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    Iterable<DataTripUserDTO> tripByVehicleAndDataAndUser(User user, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.DTO.DataTripUserDTO(u.vehicle, COUNT(u.id)) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.user=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    Iterable<DataTripUserDTO> numberOfUsesByVehicleAndDataAndUser(User user, LocalDate begin, LocalDate end);
}
