package pl.nankiewic.fleetappbackend.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumUserAccountStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account_status")
public class UserAccountStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_status", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumUserAccountStatus userAccountStatus;

    @OneToMany(mappedBy = "userAccountStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

}
