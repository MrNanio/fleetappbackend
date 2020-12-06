package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.DTO.*;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;

import java.sql.Date;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleUseRepository extends JpaRepository<VehicleUse, Long> {
    Iterable <VehicleUse> findAllByVehicle(Vehicle vehicle);
    Iterable <VehicleUse> findAllByUser(User user);
    Iterable <VehicleUse> findAllByVehicleAndUser(Vehicle vehicle, User user);
    Iterable <VehicleUse> findAllByVehicleIsAndTripDateBetween(Vehicle vehicle, Date begin, Date end);
    Iterable <VehicleUse> findAllByUserIsAndTripDateBetween(User user, Date tripDate, Date tripDate2);
    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataUseDTO(u.vehicle, SUM(u.trip)) FROM VehicleUse u WHERE u.vehicle.user=?1 and (u.tripDate between ?2 and ?3) group by u.vehicle")
    Iterable<DataUseDTO> sumOfRefuelingByVehicle(User user, Date begin, Date end);
    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataTripDTO(u.trip, u.tripDate) FROM VehicleUse u WHERE u.vehicle=?1 and (u.tripDate between ?2 and ?3) ORDER BY u.tripDate ASC")
    Iterable<DataTripDTO> tripByVehicleAndData(Vehicle vehicle, Date begin, Date end);
    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataUseTypeDTO(u.tripType, SUM(u.trip)) FROM VehicleUse u WHERE u.vehicle=?1 and (u.tripDate between ?2 and ?3) group by u.tripType")
    Iterable<DataUseTypeDTO> tripByVehicleAndDataAndTripType(Vehicle vehicle, Date begin, Date end);
    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataTripUserDTO(u.vehicle, SUM(u.trip)) FROM VehicleUse u WHERE u.user=?1 and (u.tripDate between ?2 and ?3) group by u.vehicle")
    Iterable<DataTripUserDTO> tripByVehicleAndDataAndUser(User user, Date begin, Date end);
    @Query("SELECT  new pl.nankiewic.fleetappbackend.DTO.DataTripUserDTO(u.vehicle, COUNT(u.id)) FROM VehicleUse u WHERE u.vehicle.user=?1 and (u.tripDate between ?2 and ?3) group by u.vehicle")
    Iterable<DataTripUserDTO> numberOfUsesByVehicleAndDataAndUser(User user, Date begin, Date end);
}
