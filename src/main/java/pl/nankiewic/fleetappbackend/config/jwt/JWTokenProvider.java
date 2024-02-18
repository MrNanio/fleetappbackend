package pl.nankiewic.fleetappbackend.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.nankiewic.fleetappbackend.entity.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JWTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expirationTime;

    @Value("${spring.application.name}")
    private String issuer;

    public String generateToken(User user) {
        var currentDate = new Date();
        var expirationDate = new Date(currentDate.getTime() + expirationTime);

        return Jwts.builder()
                .issuer(issuer)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .subject(user.getEmail())
                .compact();
    }

    public boolean tokenVerify(String token) {
        try {
            Jwts.parser()
                    .requireIssuer(issuer)
                    .verifyWith(getSigningKey())
                    .build()
                    .parse(token);
        } catch (JwtException ex) {
            log.warn("token is invalid");
            return false;
        }
        return true;
    }

    public Long getUserId(String token) {

        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException ex) {
            log.warn("JWT claims parser error");
        }

        return Optional.ofNullable(claims)
                .map(c -> c.get("userId", Long.class))
                .orElseThrow();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
