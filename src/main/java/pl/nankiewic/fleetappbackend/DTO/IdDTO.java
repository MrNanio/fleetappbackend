package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdDTO {
    private String email;
    private String newPassword;
    private Long id;
    private String token;

}
