package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.entity.VehicleMake;

@Repository
public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Long> {
    VehicleMake findByName(String name);
}
