package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.nankiewic.fleetappbackend.dto.vehicle.VehicleView;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findVehiclesByUser(User user);

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
            "v.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "WHERE v.id = :vehicleId")
    Optional<VehicleView> findVehicleDetailsById(Long vehicleId);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "vm.name as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vin_number as vinNumber, " +
            "v.vehicle_registration_number as vehicleRegistrationNumber, " +
            "ft.fuel_type as fuelType, " +
            "v.city_fuel_consumption as cityFuelConsumption, " +
            "v.country_fuel_consumption as countryFuelConsumption, " +
            "v.average_fuel_consumption as averageFuelConsumption, " +
            "vs.vehicle_status as vehicleStatus " +
            "FROM vehicles v " +
            "LEFT JOIN current_vehicle_users cu ON v.id = cu.vehicle_id " +
            "JOIN vehicle_makes vm ON v.vehicle_make_id = vm.id " +
            "JOIN fuel_types ft ON ft.id = v.fuel_type_id " +
            "JOIN vehicle_status vs ON vs.id = v.vehicle_status_id " +
            "WHERE v.user_id = :userId OR cu.user_id = :userId", nativeQuery = true)
    List<VehicleView> findVehicleByShareOrOwn(Long userId);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "vm.name as make, " +
            "v.model as model, " +
            "v.year as year, " +
            "v.color as color, " +
            "v.mileage as mileage, " +
            "v.vin_number as vinNumber, " +
            "v.vehicle_registration_number as vehicleRegistrationNumber, " +
            "ft.fuel_type as fuelType, " +
            "v.city_fuel_consumption as cityFuelConsumption, " +
            "v.country_fuel_consumption as countryFuelConsumption, " +
            "v.average_fuel_consumption as averageFuelConsumption, " +
            "vs.vehicle_status as vehicleStatus " +
            "FROM vehicles v " +
            "LEFT JOIN current_vehicle_users cu ON v.id = cu.vehicle_id " +
            "JOIN vehicle_makes vm ON v.vehicle_make_id = vm.id " +
            "JOIN fuel_types ft ON ft.id = v.fuel_type_id " +
            "JOIN vehicle_status vs ON vs.id = v.vehicle_status_id " +
            "WHERE cu.user_id = :userId", nativeQuery = true)
    List<VehicleView> findVehicleByShared(Long userId);

    @Query(value = "SELECT " +
            "v.id as id, " +
            "v.vehicleMake.name as make, " +
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
            "v.fuelType as fuelType, " +
            "v.cityFuelConsumption as cityFuelConsumption, " +
            "v.countryFuelConsumption as countryFuelConsumption, " +
            "v.averageFuelConsumption as averageFuelConsumption, " +
            "v.vehicleStatus as vehicleStatus " +
            "FROM Vehicle v " +
            "WHERE v.user.email=?1")
    List<VehicleView> findVehiclesDataByUserVehicleDto(String email);

    boolean existsByUser(User user);

}
