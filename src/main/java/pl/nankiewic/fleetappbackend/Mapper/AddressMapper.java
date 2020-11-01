package pl.nankiewic.fleetappbackend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.nankiewic.fleetappbackend.DTO.AddressDTO;
import pl.nankiewic.fleetappbackend.Entity.Address;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressMapper {
    @Mappings({
            @Mapping(source="id", target="id"),
            @Mapping(source="city", target="city"),
            @Mapping(source="street", target="street"),
            @Mapping(source="buildingNumber", target="buildingNumber"),
            @Mapping(source="flatNumber", target="flatNumber"),
            @Mapping(source="postalCode", target="postalCode"),
    })
    AddressDTO addressToAddressDTO(final Address address);
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "city", target = "city"),
            @Mapping(source = "street", target = "street"),
            @Mapping(source = "buildingNumber", target = "buildingNumber"),
            @Mapping(source = "flatNumber", target = "flatNumber"),
            @Mapping(source = "postalCode", target = "postalCode"),
            @Mapping(target = "userData", ignore = true)
    })
    Address addressDTOtoAddress(final AddressDTO addressDTO);
}
