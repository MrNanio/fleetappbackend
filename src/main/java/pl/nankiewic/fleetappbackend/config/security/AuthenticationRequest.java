package pl.nankiewic.fleetappbackend.config.security;

import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotNull
    private String email;
    @NotNull
    private String password;
}
