package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.Role;
import pl.nankiewic.fleetappbackend.Entity.User;

@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends JpaRepository<User, Long > {
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    User findUserByEmail(String email);
    Iterable <User> findByUser(User manager);
    User findUserById(Long user_id);
    Iterable <User> findAllByRoleIsNot(Role role);
}
