package pl.nankiewic.fleetappbackend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    private static final int EXPIRATION = 60 * 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    private LocalDateTime calculateExpiryDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMinutes(VerificationToken.EXPIRATION);
    }

    public VerificationToken(User user) {
        this.user = user;
        token = UUID.randomUUID().toString();
        expiryDate = calculateExpiryDate();
    }

}
