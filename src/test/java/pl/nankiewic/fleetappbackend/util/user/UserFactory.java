package pl.nankiewic.fleetappbackend.util.user;

import pl.nankiewic.fleetappbackend.entity.Enum.EnumRole;
import pl.nankiewic.fleetappbackend.entity.Role;
import pl.nankiewic.fleetappbackend.entity.User;
import pl.nankiewic.fleetappbackend.entity.UserAccountStatus;

public class UserFactory {

    public static User buildUser() {

        var role = Role.builder()
                .id(1L)
                .role(EnumRole.SUPERUSER)
                .build();

        var userAccountStatus = UserAccountStatus.builder()
                .id(1L)
                .build();

        return User.builder()
                .email("admin@admin.pl")
                .password("password")
                .role(role)
                .userAccountStatus(userAccountStatus)
                .build();
    }

}