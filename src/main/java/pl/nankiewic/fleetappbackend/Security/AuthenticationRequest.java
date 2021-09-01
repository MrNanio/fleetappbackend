package pl.nankiewic.fleetappbackend.Security;

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
