package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    UserData findByUser(User user);

    boolean existsById(Long id);

    boolean existsByUser(User user);
}
