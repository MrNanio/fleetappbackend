package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.InsuranceType;

@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {
    InsuranceType findByName(String name);
}
