package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
