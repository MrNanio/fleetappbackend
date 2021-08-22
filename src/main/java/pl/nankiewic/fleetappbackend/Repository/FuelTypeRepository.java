package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumFuelType;
import pl.nankiewic.fleetappbackend.Entity.FuelType;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    @Query(value = "SELECT f FROM FuelType f WHERE f.fuelType = ?1")
    FuelType findByEnumName(EnumFuelType enumFuelType);
}
