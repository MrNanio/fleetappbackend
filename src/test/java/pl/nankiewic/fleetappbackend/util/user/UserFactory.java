package pl.nankiewic.fleetappbackend.util.user;

import pl.nankiewic.fleetappbackend.entity.enums.Role;
import pl.nankiewic.fleetappbackend.entity.User;

public class UserFactory {

    public static User buildUser() {

        var role = pl.nankiewic.fleetappbackend.entity.Role.builder()
                .id(1L)
                .role(Role.SUPERUSER)
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