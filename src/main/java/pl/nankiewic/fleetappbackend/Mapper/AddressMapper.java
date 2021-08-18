package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pl.nankiewic.fleetappbackend.DTO.AddressDTO;
import pl.nankiewic.fleetappbackend.Entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO addressToAddressDTO(final Address address);

    @Mapping(target = "userData", ignore = true)
    Address addressDTOtoAddress(final AddressDTO addressDTO);
}
