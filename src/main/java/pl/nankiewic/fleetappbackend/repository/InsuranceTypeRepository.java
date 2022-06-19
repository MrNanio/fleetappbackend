package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumInsuranceType;
import pl.nankiewic.fleetappbackend.entity.InsuranceType;

import java.util.List;

@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {

    @Query(value = "SELECT s FROM InsuranceType s WHERE s.insuranceType = ?1")
    InsuranceType findByEnumName(EnumInsuranceType insuranceType);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.InsuranceTypeDTO(" +
            "i.id, " +
            "i.insuranceType) " +
            "FROM InsuranceType i")
    List<InsuranceTypeDTO> findInsurancesTypes();
}
