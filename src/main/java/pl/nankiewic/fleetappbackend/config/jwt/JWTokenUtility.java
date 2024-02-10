package pl.nankiewic.fleetappbackend.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.nankiewic.fleetappbackend.config.security.CustomUserDetails;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class JWTokenUtility {

    private static final String JWT_SECRET = "secret";
    private static final String ISSUER = "fleet-app";
    private static final int JWT_EXPIRATION_TIME = 600000;

    public static String generateJwtToken(UserDetails userDetails) {

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + JWT_EXPIRATION_TIME);
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userDetails.getUsername())
                    .withClaim("role", userDetails.getAuthorities().toString())
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;
    }

    public static String getSubject(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public static CustomUserDetails getUser(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            var role = jwt.getClaims().get("role").toString()
                    .replace("[", "")
                    .replace("\"", "")
                    .replace("]", "");
            authorities.add(new SimpleGrantedAuthority(role));
            return CustomUserDetails.builder()
                    .username(jwt.getSubject())
                    .authorities(authorities)
                    .build();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public static boolean tokenVerify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            log.error("token is invalid");
            return false;
        }
    }
}
