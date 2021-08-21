package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.VerificationToken;
import pl.nankiewic.fleetappbackend.Exception.UserAccountEnabledException;
import pl.nankiewic.fleetappbackend.Exception.UsernameAlreadyTakenException;
import pl.nankiewic.fleetappbackend.Repository.RoleRepository;
import pl.nankiewic.fleetappbackend.Repository.UserAccountStatusRepository;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Repository.VerificationTokenRepository;
import pl.nankiewic.fleetappbackend.Security.AuthenticationRequest;
import pl.nankiewic.fleetappbackend.Security.AuthenticationResponse;
import pl.nankiewic.fleetappbackend.Security.CustomUserDetailsService;
import pl.nankiewic.fleetappbackend.Security.JWTokenUtility;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    private final UserAccountStatusRepository userAccountStatusRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTokenUtility tokenUtility;

    @Autowired
    public AuthenticationService(UserAccountStatusRepository userAccountStatusRepository,
                                 VerificationTokenRepository verificationTokenRepository,
                                 RoleRepository roleRepository, UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 CustomUserDetailsService userDetailsService,
                                 JWTokenUtility tokenUtility) {
        this.userAccountStatusRepository = userAccountStatusRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtility = tokenUtility;
    }

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

        String jwtToken = tokenUtility.generateJwtToken(userDetails);

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        return new AuthenticationResponse(jwtToken, userDetails.getUsername(), user.getId(), user.getRole().getName());
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
        user.setRole(roleRepository.findRoleByName("ROLE_SUPERUSER"));
        user.setUserAccountStatus(userAccountStatusRepository.findByName("INACTIVE"));
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
