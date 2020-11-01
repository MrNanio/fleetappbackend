package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.VehicleStatus;

@CrossOrigin(origins = "http://localhost:4200")
public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Long> {
    VehicleStatus findByStatusName(String name);

}
