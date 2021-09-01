package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetChangePasswordDTO {
    private String user;
    private String new_password;
    private Long identification;
    private String ver_token;
    private String res_code;

}
