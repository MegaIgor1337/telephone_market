package mapper.address;

import dao.UserDao;
import dto.CreateAddressDto;
import entity.Address;
import lombok.NoArgsConstructor;
import mapper.Mapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)

public class CreateAddressMapper implements Mapper<CreateAddressDto, Address> {
    private static final CreateAddressMapper INSTANCE = new CreateAddressMapper();
    private final UserDao userDao = UserDao.getINSTANCE();
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    public static CreateAddressMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Address mapFrom(CreateAddressDto object) {

            return Address.builder()
                    .id(Long.valueOf(object.getId()))
                    .country(object.getCountry())
                    .city(object.getCity())
                    .street(object.getStreet())
                    .house(object.getHouse())
                    .flat(object.getFlat())
                    .user(userDao.find(sessionFactory, Long.valueOf(object.getUserId())).get())
                    .build();
    }
}
