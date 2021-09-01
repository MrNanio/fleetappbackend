package pl.nankiewic.fleetappbackend.Security;

import lombok.*;

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
    private String roles;

    public AuthenticationResponse(String token, String email, Long id, String roles) {
        this.token = token;
        this.email = email;
        this.id = id;
        this.roles = roles;
    }

}
