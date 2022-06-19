package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.nankiewic.fleetappbackend.DTO.EmailDTO;
import pl.nankiewic.fleetappbackend.DTO.UserDTO;
import pl.nankiewic.fleetappbackend.entity.Role;
import pl.nankiewic.fleetappbackend.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Iterable<User> findAllByRoleIsNot(Role role);

    Iterable<User> findByUser(User manager);

    User findUserByEmail(String email);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.UserDTO(u.email, " +
            "u.role.role, " +
            "u.id, " +
            "u.createdAt, " +
            "u.lastLoginAt, " +
            "u.userAccountStatus.userAccountStatus, " +
            "u.isEnabled) " +
            "FROM User u " +
            "WHERE u.id=?1")
    UserDTO findUserByUserId(Long id);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.UserDTO(u.email, " +
            "u.role.role, " +
            "u.id, " +
            "u.createdAt, " +
            "u.lastLoginAt, " +
            "u.userAccountStatus.userAccountStatus, " +
            "u.isEnabled) " +
            "FROM User u " +
            "WHERE u.user.email=?1")
    List<UserDTO> getUserByManagerEmail(String email);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.UserDTO(u.email, " +
            "u.role.role, " +
            "u.id, " +
            "u.createdAt, " +
            "u.lastLoginAt, " +
            "u.userAccountStatus.userAccountStatus, " +
            "u.isEnabled) " +
            "FROM User u " +
            "WHERE u.role.role=?1")
    List<UserDTO> findUsersWithoutRole(String enumRole);

    @Query(value = "SELECT new pl.nankiewic.fleetappbackend.DTO.EmailDTO(u.email) " +
            "FROM User u " +
            "WHERE u.id=?1")
    EmailDTO findUserEmailByUserId(Long id);

    boolean existsByEmail(String email);

    boolean existsById(Long id);
}
