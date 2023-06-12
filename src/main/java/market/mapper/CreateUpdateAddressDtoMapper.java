package market.mapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import market.dto.CreateUpdateAddressDto;
import market.entity.Address;
import market.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static market.util.StringContainer.*;

@Mapper(componentModel = SPRING)

public abstract class CreateUpdateAddressDtoMapper {
    @Autowired
    protected UserRepository userRepository;
    @Mapping(target = USER, expression = EXPRESSION_CREATE_UPDATE_ADDRESS)
    public abstract Address CreateUpdateAddressDtoToAddress(CreateUpdateAddressDto createUpdateAddressDto);

}
