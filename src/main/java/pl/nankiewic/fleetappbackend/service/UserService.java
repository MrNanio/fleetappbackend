package pl.nankiewic.fleetappbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.repository.RoleRepository;
import pl.nankiewic.fleetappbackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            log.error("User not exist in database");
            throw new UsernameNotFoundException("username not exist in database");
        } else {
            log.info("User exist in database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority(user.getRole().getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authority);
    }

//    public void saveUser(User user) {
//        user.setRole(roleRepository.findRoleByRole(EnumRole.USER));
//        userRepository.save(user);
//    }

//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    public Boolean userExistByEmail(String email) {
//        return userRepository.existsByEmail(email);
//    }
}
