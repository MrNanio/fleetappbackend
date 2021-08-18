package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.UserDataDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.UserData;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserDataMapper {

    @Mapping(target = "email", source = "user")
    UserDataDTO userDataToUserDataDTO(final UserData userData);

    default String userToString(User user) {
        if (user.getEmail() == null) {
            return null;
        }
        return user.getEmail();
    }

    @Mapping(target = "user", ignore = true)
    UserData userDataDTOtoUserData(final UserDataDTO userDataDTO);
}
