package pl.nankiewic.fleetappbackend.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserAccountStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 15, nullable = false)
    private String userAccountStatusName;
    @OneToMany(mappedBy = "userAccountStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccountStatusName() {
        return userAccountStatusName;
    }

    public void setUserAccountStatusName(String userAccountStatusName) {
        this.userAccountStatusName = userAccountStatusName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
