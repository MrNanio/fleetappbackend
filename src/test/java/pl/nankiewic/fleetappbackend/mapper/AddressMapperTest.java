package pl.nankiewic.fleetappbackend.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.nankiewic.fleetappbackend.DTO.AddressDTO;
import pl.nankiewic.fleetappbackend.entity.Address;
import static org.assertj.core.api.Assertions.assertThat;

class AddressMapperTest {
    private final AddressMapper addressMapper= Mappers.getMapper(AddressMapper.class);
    @Test
    void should_map_address_to_address_dto(){
        //given
        Address address = new Address();
        address.setId(1L);
        address.setCity("Lublin");
        address.setStreet("Kwiatowa");
        address.setBuildingNumber("1");
        address.setFlatNumber("12");
        address.setPostalCode("20-677");
        //when
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);
        //then
        assertThat(addressDTO.getCity()).isEqualTo("Lublin");
    }
    @Test
    void should_map_address_dto_to_address(){
        //given
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity("Kraków");
        addressDTO.setStreet("Bocianów");
        addressDTO.setBuildingNumber("2");
        addressDTO.setFlatNumber("2");
        addressDTO.setPostalCode("23-333");
        addressDTO.setId(2L);
        //when
        Address address = addressMapper.addressDTOtoAddress(addressDTO);
        //then
        assertThat(addressDTO.getCity()).isEqualTo("Kraków");
    }

}