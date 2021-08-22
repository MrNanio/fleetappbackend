package pl.nankiewic.fleetappbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumUserAccountStatus;
import pl.nankiewic.fleetappbackend.Entity.UserAccountStatus;

@Repository
public interface UserAccountStatusRepository extends JpaRepository<UserAccountStatus, Long> {

    @Query(value = "SELECT s FROM UserAccountStatus s WHERE s.userAccountStatus = ?1")
    UserAccountStatus findByEnumName(EnumUserAccountStatus enumUserAccountStatus);
}
