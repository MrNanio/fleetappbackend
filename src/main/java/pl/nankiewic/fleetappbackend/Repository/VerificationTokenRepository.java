package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
    boolean existsByToken(String token);
    boolean existsVerificationTokenByTokenIs(String token);
}
