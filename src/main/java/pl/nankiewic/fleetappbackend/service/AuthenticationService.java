package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumRole;
import pl.nankiewic.fleetappbackend.entity.Enum.EnumUserAccountStatus;
import pl.nankiewic.fleetappbackend.entity.Role;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.VerificationToken;
import pl.nankiewic.fleetappbackend.Exception.UserAccountEnabledException;
import pl.nankiewic.fleetappbackend.Exception.UsernameAlreadyTakenException;
import pl.nankiewic.fleetappbackend.repository.RoleRepository;
import pl.nankiewic.fleetappbackend.repository.UserAccountStatusRepository;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VerificationTokenRepository;
import pl.nankiewic.fleetappbackend.config.security.AuthenticationRequest;
import pl.nankiewic.fleetappbackend.config.security.AuthenticationResponse;
import pl.nankiewic.fleetappbackend.config.security.CustomUserDetailsService;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenUtility;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private final UserAccountStatusRepository userAccountStatusRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findUserByEmail((authenticationRequest.getEmail()));
        if (!user.isEnabled()) {
            throw new UserAccountEnabledException("Konto: " + user.getEmail() + " jest nieaktywne");
        }

        String jwtToken = JWTokenUtility.generateJwtToken(userDetails);

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        return new AuthenticationResponse(jwtToken,
                userDetails.getUsername(),
                user.getId(),
                user.getRole().getRole().name());
    }

    public void save(AuthenticationRequest authenticationRequest) {

        if (userRepository.existsByEmail(authenticationRequest.getEmail())) {
            throw new UsernameAlreadyTakenException("Użytkownik istnieje " + authenticationRequest.getEmail());
        }

        User user = new User();
        user.setEmail(authenticationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
        superuserRegister(user);
    }

    private void superuserRegister(User user) {
        user.setCreatedAt(LocalDateTime.now());

        Role role = roleRepository.findRoleByRole(EnumRole.SUPERUSER);

        user.setRole(role);
        user.setUserAccountStatus(userAccountStatusRepository.findByEnumName(EnumUserAccountStatus.INACTIVE));
        user.setEnabled(true);
        userRepository.save(user);
        activationToken(userRepository.findUserByEmail(user.getEmail()));
    }

    private void activationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
        //mailService.sendActivationEmail(user.getEmail(),verificationTokenRepository.findByUser(user).getToken());
    }

}
