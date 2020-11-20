package pl.nankiewic.fleetappbackend.Entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "verifcation_tokens")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private LocalDateTime expiryDate;

    private LocalDateTime calculateExpiryDate() {
        LocalDateTime localDateTime=LocalDateTime.now();
        return localDateTime.plusMinutes(VerificationToken.EXPIRATION);
    }

    public VerificationToken() {
    }

    public VerificationToken(User user){
        this.user =user;
        token = UUID.randomUUID().toString();
        expiryDate=calculateExpiryDate();
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
