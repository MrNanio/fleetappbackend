package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumRole;
import pl.nankiewic.fleetappbackend.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT r FROM Role r WHERE r.role = ?1")
    Role findRoleByEnumName(String enumRole);

    Role findRoleByRole(EnumRole enumRole);

}
