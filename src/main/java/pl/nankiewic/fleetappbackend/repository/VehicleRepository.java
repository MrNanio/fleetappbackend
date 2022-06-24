package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.DTO.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Iterable<Vehicle> findVehiclesByUser(User user);

    Optional<Vehicle> findById(Long id);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "v.vehicleMake.name as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vinNumber as vinNumber, " +
            "v.vehicleRegistrationNumber as vehicleRegistrationNumber, " +
            "v.fuelType.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "WHERE v.id = :id")
    Optional<VehicleView> findVehicleDetailsById(Long id);

    @Query(value = "SELECT distinct " +
            "v.id as id, " +
            "v.vehicleMake.name as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vinNumber as vinNumber, " +
            "v.vehicleRegistrationNumber as vehicleRegistrationNumber, " +
            "v.fuelType.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v JOIN v.currentVehicleUser cu " +
            "WHERE v.id = :userId or cu.user.id = :userId")
    Optional<VehicleView> findVehicleShareAndOwn(Long userId);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "v.vehicleMake.name as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vinNumber as vinNumber, " +
            "v.vehicleRegistrationNumber as vehicleRegistrationNumber, " +
            "v.fuelType.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "WHERE v.user.email=?1")
    List<VehicleView> findVehiclesDataByUser(String email);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "v.vehicleMake.name as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vinNumber as vinNumber, " +
            "v.vehicleRegistrationNumber as vehicleRegistrationNumber, " +
            "v.fuelType.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "WHERE v.user.email=?1")
    List<VehicleDTO> findVehiclesDataByUserVehicleDto(String email);

    boolean existsByUser(User user);

}
