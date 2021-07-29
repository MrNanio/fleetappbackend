package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.AddressDTO;
import pl.nankiewic.fleetappbackend.Entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mappings({
    })
    AddressDTO addressToAddressDTO(final Address address);
    @Mappings({
            @Mapping(target = "userData", ignore = true)
    })
    Address addressDTOtoAddress(final AddressDTO addressDTO);
}
