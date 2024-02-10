package pl.nankiewic.fleetappbackend.dto.user;

import pl.nankiewic.fleetappbackend.entity.enums.Role;
import pl.nankiewic.fleetappbackend.entity.enums.UserAccountStatus;

import java.time.LocalDateTime;

public interface UserView {

    Long getId();

    String getEmail();

    Role getRole();

    LocalDateTime getCreatedAt();

    LocalDateTime getLastLoginAt();

    UserAccountStatus getUserStatus();

    boolean getEnabled();
}
