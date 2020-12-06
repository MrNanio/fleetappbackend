package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;

public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Long> {
    VehicleMake findByName(String name);
}
