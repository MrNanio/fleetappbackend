package pl.nankiewic.fleetappbackend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.UserData;

@CrossOrigin(origins = "http://localhost:4200")
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    @Query("select d from UserData d where d.user = ?1")
    UserData findByUser(User user);
    boolean existsById(Long id);
    boolean existsByUser(User user);
    UserData deleteByUser(User user);



}
