package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.nankiewic.fleetappbackend.dto.user.UserView;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT u.id as id, " +
            "u.email as email, " +
            "u.role as role, " +
            "u.createdAt as createdAt, " +
            "u.lastLoginAt as lastLoginAt, " +
            "u.userAccountStatus as userStatus, " +
            "u.enabled as enabled " +
            "FROM User u " +
            "WHERE u.id = :id")
    Optional<UserView> findUserViewByUserId(Long id);

    @Query(value = "SELECT u.id as id, " +
            "u.email as email, " +
            "u.role as role, " +
            "u.createdAt as createdAt, " +
            "u.lastLoginAt as lastLoginAt, " +
            "u.userAccountStatus as userStatus, " +
            "u.enabled as enabled " +
            "FROM User u " +
            "JOIN u.parentUser pu " +
            "WHERE pu.email = :email")
    List<UserView> getUserViewsByParentUserEmail(String email);

    @Query(value = "SELECT u.id as id, " +
            "u.email as email, " +
            "u.role as role, " +
            "u.createdAt as createdAt, " +
            "u.lastLoginAt as lastLoginAt, " +
            "u.userAccountStatus as userStatus, " +
            "u.enabled as enabled " +
            "FROM User u " +
            "WHERE u.role <> :role")
    List<UserView> findUserViewsWithoutRole(Role role);

//    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.dto.account.PasswordRecoveryDTO(u.email) " +
//            "FROM User u " +
//            "WHERE u.id=?1")
//    PasswordRecoveryDTO findUserEmailByUserId(Long id);

    boolean existsByEmail(String email);

    boolean existsById(Long id);
}
