package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumVehicleStatus;
import pl.nankiewic.fleetappbackend.Entity.VehicleStatus;

@Repository
public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Long> {

    @Query(value = "SELECT s FROM VehicleStatus s WHERE s.vehicleStatus = ?1")
    VehicleStatus findByEnumName(EnumVehicleStatus enumVehicleStatus);
}
