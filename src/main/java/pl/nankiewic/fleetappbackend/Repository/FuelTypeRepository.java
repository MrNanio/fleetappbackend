package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.FuelType;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    FuelType findByName(String name);
}
