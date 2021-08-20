package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.MessageResponse;
import pl.nankiewic.fleetappbackend.Security.AuthenticationRequest;
import pl.nankiewic.fleetappbackend.Security.AuthenticationResponse;
import pl.nankiewic.fleetappbackend.Service.AuthenticationService;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest authenticationRequest) {

        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/signup")
    public MessageResponse registerUser(@RequestBody AuthenticationRequest authenticationRequest) {

        authenticationService.save(authenticationRequest);
        return new MessageResponse("ok", LocalDateTime.now());
    }

}
