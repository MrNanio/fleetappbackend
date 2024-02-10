package pl.nankiewic.fleetappbackend.entity;
import lombok.*;
import pl.nankiewic.fleetappbackend.entity.enums.Role;
import pl.nankiewic.fleetappbackend.entity.enums.UserAccountStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "password", length = 45)
    private String password;

    @Column(name = "reset_code", length = 45)
    private String resetCode;

    @Column(name = "reset_at")
    private LocalDateTime resetAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "is_enabled")
    private boolean enabled;

    @Column(name = "account_status", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserAccountStatus userAccountStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User parentUser;

    @OneToMany(mappedBy = "parentUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private UserData userData;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleUse> vehicleUses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CurrentVehicleUser> currentVehicleUsers = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private VerificationToken verificationToken;

    @Column(name = "role", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleRefueling> refuelings = new HashSet<>();


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User updateLastLoginAt() {
        this.setLastLoginAt(LocalDateTime.now());
        return this;
    }

}
