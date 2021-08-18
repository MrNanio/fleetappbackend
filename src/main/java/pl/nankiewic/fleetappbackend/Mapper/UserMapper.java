package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.UserDTO;
import pl.nankiewic.fleetappbackend.Entity.Role;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.UserAccountStatus;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

        @Mapping(target = "roles", source = "role")
        @Mapping(target = "userStatus", source = "userAccountStatus")
        UserDTO userToUserDTO(final User user);

        default String roleToString(Role roles) {
                if (roles == null) {
                        return null;
                }
                return roles.getName();
        }

        default String userStatusToString(UserAccountStatus accountStatus) {
                if (accountStatus == null) {
                        return null;
                }
                return accountStatus.getName();
        }

        Iterable<UserDTO> userToUserDTOs(Iterable<User> users);

}
