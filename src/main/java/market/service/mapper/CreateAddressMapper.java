package market.service.mapper;

import market.service.dto.CreateAddressDto;
import market.model.entity.Address;
import market.model.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static market.service.util.ConstantContainer.*;


@Mapper(componentModel = SPRING)
public abstract class CreateAddressMapper  {

    @Autowired
    protected  UserRepository userRepository;

    @Mapping(target = USER, expression = EXPRESSION_CREATE_ADDRESS)
    public abstract Address createAddressDtoToAddress(CreateAddressDto createAddressDto);


}
