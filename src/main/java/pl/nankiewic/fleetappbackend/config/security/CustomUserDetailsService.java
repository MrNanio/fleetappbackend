package pl.nankiewic.fleetappbackend.config.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.entity.enums.Role;
import pl.nankiewic.fleetappbackend.repository.UserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final String NOT_FOUND = "user.not.found";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("user exist in database: {}", email);
        var user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(NOT_FOUND));
        var authorities = getAuthorities(user.getRole());
        var locked = !user.isEnabled();

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(locked)
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));

        return authorities;
    }

}