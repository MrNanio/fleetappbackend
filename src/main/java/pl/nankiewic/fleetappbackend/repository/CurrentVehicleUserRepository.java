package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.entity.CurrentVehicleUser;
import pl.nankiewic.fleetappbackend.entity.Vehicle;

import java.util.List;

@Repository
public interface CurrentVehicleUserRepository extends JpaRepository<CurrentVehicleUser, Long> {
    List<CurrentVehicleUser> findCurrentVehicleUsersByVehicleIs(Vehicle vehicle);

    @Query(value = "SELECT c FROM CurrentVehicleUser c WHERE c.vehicle.user.email=?1")
    List<CurrentVehicleUser> findCurrentVehicleUsersByVehicleOwner(String email);

    @Query(value = "SELECT c FROM CurrentVehicleUser c WHERE c.user.id=?1")
    List<CurrentVehicleUser> findCurrentVehicleUsersByUserIs(Long userId);

    @Query(value = "SELECT c FROM CurrentVehicleUser c WHERE c.vehicle.id=?1")
    CurrentVehicleUser findByVehicle(Long vehicleId);

    boolean existsByVehicle_Id(Long id);
}
