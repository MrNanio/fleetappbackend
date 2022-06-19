package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumVehicleStatus;
import pl.nankiewic.fleetappbackend.entity.VehicleStatus;

@Repository
public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Long> {

    @Query(value = "SELECT s FROM VehicleStatus s WHERE s.vehicleStatus = ?1")
    VehicleStatus findByEnumName(EnumVehicleStatus enumVehicleStatus);
}
