package pl.nankiewic.fleetappbackend.DTO;

import lombok.*;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumRole;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumUserAccountStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @NotNull
    private String email;
    @NotNull
    private EnumRole roles;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private EnumUserAccountStatus userStatus;
    private boolean isEnabled;

}
