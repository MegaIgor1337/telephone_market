package market.mapper;

import market.dto.CreateAddressDto;
import market.entity.Address;
import market.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static market.util.StringContainer.*;


@Mapper(componentModel = SPRING)
public abstract class CreateAddressMapper  {

    @Autowired
    protected  UserRepository userRepository;

    @Mapping(target = USER, expression = EXPRESSION_CREATE_ADDRESS)
    public abstract Address createAddressDtoToAddress(CreateAddressDto createAddressDto);


}
