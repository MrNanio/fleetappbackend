package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.UserAccountStatus;

@Repository
public interface UserAccountStatusRepository extends JpaRepository<UserAccountStatus, Long> {
    UserAccountStatus findByName(String s);
}
