package market.mapper;

import market.dto.AddressDto;
import market.entity.Address;
import org.mapstruct.Mapper;

import static market.util.StringContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {UserMapper.class, AddressMapper.class, OrderMapper.class})
public interface AddressMapper {
    AddressDto addressToAddressDto(Address address);
    Address addressDtoToAddress(AddressDto addressDto);
//model mapper
}
