package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.UserDTO;
import pl.nankiewic.fleetappbackend.Entity.Role;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.UserAccountStatus;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
        @Mappings({
                @Mapping(source = "id", target = "id"),
                @Mapping(source = "email", target = "email"),
                @Mapping(target = "createdAt", source = "createdAt"),
                @Mapping(target = "lastLoginAt", source = "lastLoginAt"),
                @Mapping(target = "roles", source = "role"),
                @Mapping(target = "enabled", source = "enabled"),
                @Mapping(target = "userStatus", source = "userAccountStatus")
        })
        UserDTO userToUserDTO(final User user);
        default String roleToString(Role roles) {
                if (roles == null) {
                        return null;
                }
                return roles.getRoleName();
        }
        default String userStatusToString(UserAccountStatus accountStatus) {
                if (accountStatus == null) {
                        return null;
                }
                return accountStatus.getUserAccountStatusName();
        }
        Iterable<UserDTO> userToUserDTOs(Iterable<User> users);

}
