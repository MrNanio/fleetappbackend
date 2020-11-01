package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.InsuranceType;

@CrossOrigin(origins = "http://localhost:4200")
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {
    InsuranceType findByName(String name);
}
