package pl.nankiewic.fleetappbackend.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.nankiewic.fleetappbackend.config.security.CustomUserDetails;
import pl.nankiewic.fleetappbackend.entity.enums.Role;
import pl.nankiewic.fleetappbackend.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";

    private final JWTokenProvider tokenProvider;
    private final UserRepository userRepository;

    public JwtAuthorizationFilter(final JWTokenProvider tokenProvider,
                                  final UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = parseJWToken(request);

        Optional.ofNullable(token)
                .filter(tokenProvider::tokenVerify)
                .map(tokenProvider::getUserId)
                .ifPresent(userId -> setSecurityContextHolder(request, userId));

        filterChain.doFilter(request, response);
    }

    private String parseJWToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTH_HEADER))
                .filter(header -> StringUtils.hasText(header) && header.startsWith(HEADER_PREFIX))
                .map(h -> h.replace(HEADER_PREFIX, ""))
                .orElse(null);
    }

    private void setSecurityContextHolder(HttpServletRequest request, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        var userDetails = CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getEmail())
                .authorities(getAuthorities(user.getRole()))
                .build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }

}