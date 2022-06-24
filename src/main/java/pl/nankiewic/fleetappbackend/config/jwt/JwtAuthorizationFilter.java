package pl.nankiewic.fleetappbackend.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import pl.nankiewic.fleetappbackend.config.security.CustomUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = parseJwt(request);
        if (jwt != null && JWTokenUtility.tokenVerify(jwt)) {
            Optional.of(jwt)
                    .filter(uname -> Objects.isNull(SecurityContextHolder.getContext().getAuthentication()))
                    .filter(JWTokenUtility::tokenVerify)
                    .map(JWTokenUtility::getUser)
                    .ifPresent(userJwtDTO -> setSecurityContextHolder(request, userJwtDTO));
        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContextHolder(HttpServletRequest request, CustomUserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(header) && header.startsWith(HEADER_PREFIX)) {
            return header.replace(HEADER_PREFIX, "");
        } else {
            log.error("header is invalid");
            return null;
        }
    }
}
