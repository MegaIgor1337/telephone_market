package market.service.mapper;

import market.service.dto.CreateUpdateAddressDto;
import market.model.entity.Address;
import market.model.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static market.service.util.ConstantContainer.*;

@Mapper(componentModel = SPRING)

public abstract class CreateUpdateAddressDtoMapper {
    @Autowired
    protected UserRepository userRepository;
    @Mapping(target = USER, expression = EXPRESSION_CREATE_UPDATE_ADDRESS)
    public abstract Address CreateUpdateAddressDtoToAddress(CreateUpdateAddressDto createUpdateAddressDto);

}
