package market.mapper;

import market.dto.AddressDto;
import market.entity.Address;
import org.mapstruct.Mapper;

import static market.util.StringContainer.SPRING;

@Mapper(componentModel = SPRING, uses = UserMapper.class)
public interface AddressMapper {
    AddressDto mapFrom(Address address);
//model mapper
}
