package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.config.security.AuthenticationRequest;
import pl.nankiewic.fleetappbackend.config.security.AuthenticationResponse;
import pl.nankiewic.fleetappbackend.service.AuthenticationService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public AuthenticationResponse loginUser(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/signup")
    public void registerUser(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        authenticationService.createSuperuser(authenticationRequest);
    }

}
