package pl.nankiewic.fleetappbackend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.UserData;

@CrossOrigin(origins = "http://localhost:4200")
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    UserData findByUser(User user);
    boolean existsById(Long id);
    boolean existsByUser(User user);
}
