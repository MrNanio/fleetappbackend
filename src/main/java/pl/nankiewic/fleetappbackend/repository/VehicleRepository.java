package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findById(Long id);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "v.make as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vinNumber as vinNumber, " +
            "v.vehicleRegistrationNumber as vehicleRegistrationNumber, " +
            "v.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "WHERE v.id = :vehicleId")
    Optional<VehicleView> findVehicleViewById(Long vehicleId);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "v.make as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vinNumber as vinNumber, " +
            "v.vehicleRegistrationNumber as vehicleRegistrationNumber, " +
            "v.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "JOIN v.user u " +
            "LEFT JOIN v.currentVehicleUser cvu " +
            "LEFT JOIN cvu.user cu " +
            "WHERE u.id = :userId OR cu.id = :userId")
    List<VehicleView> findVehicleViewsByCurrentUserIdOrOwnerId(Long userId);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "v.make as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vinNumber as vinNumber, " +
            "v.vehicleRegistrationNumber as vehicleRegistrationNumber, " +
            "v.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "JOIN v.currentVehicleUser cvu " +
            "JOIN cvu.user u " +
            "WHERE u.id = :userId")
    List<VehicleView> findVehicleViewsByCurrentUserId(Long userId);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "v.make as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vinNumber as vinNumber, " +
            "v.vehicleRegistrationNumber as vehicleRegistrationNumber, " +
            "v.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "JOIN v.user u " +
            "WHERE u.id = :userId")
    List<VehicleView> findVehicleViewsByUser(Long userId);

    @Query("SELECT " +
            "CASE WHEN COUNT(v) > 0 " +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM Vehicle v " +
            "JOIN v.user u " +
            "where u.id = :userId and v.id = :vehicleId")
    boolean existsByUserIdAndVehicleId(Long userId, Long vehicleId);

    boolean existsByUser(User user);

}