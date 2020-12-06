package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.Address;

@CrossOrigin(origins = "http://localhost:4200")
public interface AddressRepository extends JpaRepository<Address, Long> {
}
