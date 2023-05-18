package mapper.address;

import dao.UserRepository;
import dto.CreateAddressDto;
import entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mapper.Mapper;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)

public class CreateAddressMapper implements Mapper<CreateAddressDto, Address> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Getter
    private static final CreateAddressMapper INSTANCE = new CreateAddressMapper();
    private final UserRepository userDao = new UserRepository(HibernateUtil
            .getSessionFromFactory(sessionFactory));


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
