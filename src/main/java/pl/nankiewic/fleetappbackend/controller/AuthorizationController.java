package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok()
                .body(authenticationService.login(authenticationRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        authenticationService.createSuperuser(authenticationRequest);
        return ResponseEntity.ok("Success");
    }

}
