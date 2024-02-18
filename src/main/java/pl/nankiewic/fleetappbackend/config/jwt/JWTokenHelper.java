package pl.nankiewic.fleetappbackend.config.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.nankiewic.fleetappbackend.config.security.CustomUserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JWTokenHelper {

    public static Long getJWTUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (CustomUserDetails) authentication.getPrincipal();

        return principal.getId();
    }

}