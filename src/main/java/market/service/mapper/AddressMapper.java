package market.service.mapper;

import market.service.dto.AddressDto;
import market.model.entity.Address;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {UserMapper.class, AddressMapper.class, OrderMapper.class})
public interface AddressMapper {
    AddressDto addressToAddressDto(Address address);
    Address addressDtoToAddress(AddressDto addressDto);
//model mapper
}
