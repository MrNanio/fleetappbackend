package pl.nankiewic.fleetappbackend.dto.account;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDTO {
    private String newPassword;
    private String oldPassword;

}
