package pl.nankiewic.fleetappbackend.config.security;

import lombok.*;
import pl.nankiewic.fleetappbackend.entity.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    private String token;
    private String type = "Bearer";
    private String email;
    private Long id;
    private Role role;

}
