package pl.nankiewic.fleetappbackend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.nankiewic.fleetappbackend.Entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long > {
    Privilege findByName(String name);
}
