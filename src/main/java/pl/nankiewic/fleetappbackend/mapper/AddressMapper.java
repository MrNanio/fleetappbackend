package pl.nankiewic.fleetappbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pl.nankiewic.fleetappbackend.DTO.AddressDTO;
import pl.nankiewic.fleetappbackend.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO addressToAddressDTO(Address address);
    @Mapping(target = "userData", ignore = true)
    Address addressDTOtoAddress(AddressDTO addressDTO);
}
