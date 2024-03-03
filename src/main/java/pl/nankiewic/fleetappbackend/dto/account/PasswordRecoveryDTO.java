package pl.nankiewic.fleetappbackend.dto.account;

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
