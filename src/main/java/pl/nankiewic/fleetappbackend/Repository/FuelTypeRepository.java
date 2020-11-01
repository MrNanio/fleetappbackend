package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.FuelType;

@CrossOrigin(origins = "http://localhost:4200")
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    FuelType findByName(String name);
}
