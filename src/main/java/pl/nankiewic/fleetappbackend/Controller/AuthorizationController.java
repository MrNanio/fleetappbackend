package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.MessageResponse;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Exception.UserAccountEnabledException;
import pl.nankiewic.fleetappbackend.Exception.UsernameAlreadyTakenException;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Security.AuthenticationRequest;
import pl.nankiewic.fleetappbackend.Security.AuthenticationResponse;
import pl.nankiewic.fleetappbackend.Service.AccountService;
import pl.nankiewic.fleetappbackend.Service.CustomUserDetailsService;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AccountService accountService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthorizationController(AccountService accountService, UserRepository userRepository,
                                   AuthenticationManager authenticationManager,
                                   CustomUserDetailsService customUserDetailsService) {
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/signin")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest authenticationRequest) {
        if (!userRepository.existsByEmail(authenticationRequest.getEmail())) {
            throw new UsernameNotFoundException("Użytkownik nie istnieje:");
        }
        User user = userRepository.findUserByEmail((authenticationRequest.getEmail()));
        if (!user.isEnabled()) {
            throw new UserAccountEnabledException("Konto: " + authenticationRequest.getEmail() + " jest nieaktywne");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return accountService.login(authentication);
    }

    @PostMapping("/signup")
    public MessageResponse registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
        if (userRepository.existsByEmail(authenticationRequest.getEmail())) {
            throw new UsernameAlreadyTakenException("Użytkownik istnieje " + authenticationRequest.getEmail());
        }
        customUserDetailsService.save(authenticationRequest);
        return new MessageResponse("ok", LocalDateTime.now());
    }

}
