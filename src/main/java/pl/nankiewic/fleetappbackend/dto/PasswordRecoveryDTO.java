package pl.nankiewic.fleetappbackend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordRecoveryDTO {

    private String email;

    private String password;

    private String repeatedPassword;

    private String code;

}
