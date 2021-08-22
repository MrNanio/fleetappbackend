package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumInsuranceType;
import pl.nankiewic.fleetappbackend.Entity.InsuranceType;

@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {

    @Query(value = "SELECT s FROM InsuranceType s WHERE s.insuranceType = ?1")
    InsuranceType findByEnumName(EnumInsuranceType insuranceType);
}
