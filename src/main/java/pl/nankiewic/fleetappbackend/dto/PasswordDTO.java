package pl.nankiewic.fleetappbackend.dto;

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
