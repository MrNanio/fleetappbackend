package pl.nankiewic.fleetappbackend.entity;

import lombok.*;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumUserAccountStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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
