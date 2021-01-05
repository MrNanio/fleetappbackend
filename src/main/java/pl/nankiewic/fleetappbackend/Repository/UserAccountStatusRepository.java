package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.UserAccountStatus;

@CrossOrigin(origins = "http://localhost:4200")
public interface UserAccountStatusRepository extends JpaRepository<UserAccountStatus, Long> {
    UserAccountStatus findByName(String s);
}
