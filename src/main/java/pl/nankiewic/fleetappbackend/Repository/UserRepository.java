package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.nankiewic.fleetappbackend.Entity.Role;
import pl.nankiewic.fleetappbackend.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Iterable<User> findAllByRoleIsNot(Role role);

    Iterable<User> findByUser(User manager);

    User findUserByEmail(String email);

    User findUserById(Long user_id);

    boolean existsByEmail(String email);

    boolean existsById(Long id);
}
