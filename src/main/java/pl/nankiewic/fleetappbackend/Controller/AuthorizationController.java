package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.MessageResponse;
import pl.nankiewic.fleetappbackend.Exception.UsernameAlreadyTakenException;
import pl.nankiewic.fleetappbackend.Repository.UserRepository;
import pl.nankiewic.fleetappbackend.Security.AuthenticationRequest;
import pl.nankiewic.fleetappbackend.Security.AuthenticationResponse;
import pl.nankiewic.fleetappbackend.Service.AuthenticationService;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthorizationController(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest authenticationRequest) {

        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/signup")
    public MessageResponse registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
        if (userRepository.existsByEmail(authenticationRequest.getEmail())) {
            throw new UsernameAlreadyTakenException("Użytkownik istnieje " + authenticationRequest.getEmail());
        }
        authenticationService.save(authenticationRequest);
        return new MessageResponse("ok", LocalDateTime.now());
    }

}
