package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.UserDTO;
import pl.nankiewic.fleetappbackend.Entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
        @Mappings({
                @Mapping(source = "id", target = "id"),
                @Mapping(source = "email", target = "email"),
                @Mapping(target = "createdAt", source = "createdAt")
        })
        UserDTO userToUserDTO(final User user);
        Iterable<UserDTO> userToUserDTOs(Iterable<User> users);

}
