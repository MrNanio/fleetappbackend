package pl.nankiewic.fleetappbackend.config.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.entity.Role;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.repository.UserRepository;

import java.util.*;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!userRepository.existsByEmail(email)) {
            throw new UsernameNotFoundException("Użytkownik nie istnieje");
        }
        User user = userRepository.findUserByEmail((email));
        return new CustomUserDetails(user.getId(),
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user.getRole()),
                user.isEnabled());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRole().name()));
        return authorities;
    }
}
