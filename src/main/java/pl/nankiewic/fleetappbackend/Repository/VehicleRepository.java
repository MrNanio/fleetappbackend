package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Iterable<Vehicle> findVehiclesByUser(User user);
    Optional<Vehicle> findById(Long id);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.VehicleDTO(" +
            "v.id, " +
            "v.vehicleMake.name, " +
            "v.model, " +
            "v.year, " +
            "v.color, " +
            "v.mileage, " +
            "v.vinNumber, " +
            "v.vehicleRegistrationNumber, " +
            "v.fuelType.name, " +
            "v.cityFuelConsumption, " +
            "v.countryFuelConsumption, " +
            "v.averageFuelConsumption, " +
            "v.vehicleStatus.name) " +
            "FROM Vehicle v " +
            "WHERE v.id=?1")
    VehicleDTO selectVehicleDetailsById(Long id);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.VehicleDTO(" +
            "v.id, " +
            "v.vehicleMake.name, " +
            "v.model, " +
            "v.year, " +
            "v.color, " +
            "v.mileage, " +
            "v.vinNumber, " +
            "v.vehicleRegistrationNumber, " +
            "v.fuelType.name, " +
            "v.cityFuelConsumption, " +
            "v.countryFuelConsumption, " +
            "v.averageFuelConsumption, " +
            "v.vehicleStatus.name) " +
            "FROM Vehicle v " +
            "WHERE v.user=?1")
    List<VehicleDTO> selectVehiclesDataByUser(User user);

    boolean existsByUser(User user);

}
