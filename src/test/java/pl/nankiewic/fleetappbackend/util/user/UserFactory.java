package pl.nankiewic.fleetappbackend.util.user;

import pl.nankiewic.fleetappbackend.entity.enums.Role;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.enums.UserAccountStatus;

public class UserFactory {

    public static User buildUser() {
        return User.builder()
                .email("admin@admin.pl")
                .password("password")
                .role(Role.SUPERUSER)
                .userAccountStatus(UserAccountStatus.ACTIVE)
                .build();
    }

}