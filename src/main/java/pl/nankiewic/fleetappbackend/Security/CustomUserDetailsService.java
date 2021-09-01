package pl.nankiewic.fleetappbackend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.Entity.Role;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRole().name()));
        return authorities;
    }
}
