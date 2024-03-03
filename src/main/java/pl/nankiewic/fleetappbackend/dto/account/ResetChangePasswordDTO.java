package pl.nankiewic.fleetappbackend.dto.account;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetChangePasswordDTO {
    @NotBlank
    private String user;
    private String new_password;
    private Long identification;
    private String ver_token;
    private String res_code;

}
