package market.mapper.address;

import market.repository.UserRepository;
import market.dto.CreateAddressDto;
import market.entity.Address;
import lombok.RequiredArgsConstructor;
import market.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAddressMapper implements Mapper<CreateAddressDto, Address> {


    private final UserRepository userDao;


    @Override
    public Address mapFrom(CreateAddressDto object) {

            return Address.builder()
                    .id(Long.valueOf(object.getId()))
                    .country(object.getCountry())
                    .city(object.getCity())
                    .street(object.getStreet())
                    .house(object.getHouse())
                    .flat(object.getFlat())
                    .user(userDao.findById(Long.valueOf(object.getUserId())).get())
                    .build();
    }
}
