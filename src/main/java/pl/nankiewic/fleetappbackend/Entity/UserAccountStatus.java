package pl.nankiewic.fleetappbackend.Entity;

import pl.nankiewic.fleetappbackend.Entity.Enum.EnumUserAccountStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_account_status")
public class UserAccountStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_status", length = 15, nullable = false)
    private EnumUserAccountStatus userAccountStatus;

    @OneToMany(mappedBy = "userAccountStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumUserAccountStatus getUserAccountStatus() {
        return userAccountStatus;
    }

    public void setUserAccountStatus(EnumUserAccountStatus userAccountStatus) {
        this.userAccountStatus = userAccountStatus;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
