package pl.nankiewic.fleetappbackend.service;

import io.vavr.Tuple;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenUtility;
import pl.nankiewic.fleetappbackend.config.security.AuthenticationRequest;
import pl.nankiewic.fleetappbackend.config.security.AuthenticationResponse;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.VerificationToken;
import pl.nankiewic.fleetappbackend.entity.enums.Role;
import pl.nankiewic.fleetappbackend.entity.enums.UserAccountStatus;
import pl.nankiewic.fleetappbackend.repository.UserRepository;
import pl.nankiewic.fleetappbackend.repository.VerificationTokenRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private static final String BEARER_TYPE = "Bearer";

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        return Optional.of(auth)
                .map(authenticationManager::authenticate)
                .map(this::manageSuccessfulLogin)
                .orElseThrow();
    }

    @Transactional
    public void createSuperuser(AuthenticationRequest authenticationRequest) {
        Optional.of(authenticationRequest)
                .map(this::buildUser)
                .map(userRepository::save)
                .map(VerificationToken::new)
                .ifPresent(verificationTokenRepository::save);
        //todo mailService.sendActivationEmail(user.getEmail(),verificationTokenRepository.findByUser(user).getToken());
    }


    private AuthenticationResponse manageSuccessfulLogin(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails = (UserDetails) authentication.getPrincipal();
        var token = JWTokenUtility.generateJwtToken(userDetails);

        return userRepository.findUserByEmail(userDetails.getUsername())
                .map(User::updateLastLoginAt)
                .map(userRepository::save)
                .map(user -> Tuple.of(user, token))
                .map(t -> buildAuthenticationResponse(t._1, t._2))
                .orElseThrow();
    }

    private AuthenticationResponse buildAuthenticationResponse(User user, String token) {
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .id(user.getId())
                .role(user.getRole())
                .type(BEARER_TYPE)
                .build();
    }

    private User buildUser(AuthenticationRequest authenticationRequest) {
        return User.builder()
                .email(authenticationRequest.getEmail())
                .password(passwordEncoder.encode(authenticationRequest.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(Role.SUPERUSER)
                .userAccountStatus(UserAccountStatus.INACTIVE)
                .enabled(true)
                .build();
    }

}