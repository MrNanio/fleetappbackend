package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumFuelType;
import pl.nankiewic.fleetappbackend.entity.FuelType;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    @Query(value = "SELECT f FROM FuelType f WHERE f.fuelType = ?1")
    FuelType findByEnumName(EnumFuelType enumFuelType);
}
