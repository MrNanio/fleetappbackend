package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.VehicleMake;

@Repository
public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Long> {
    VehicleMake findByName(String name);
}
