package service;

import dao.AddressDao;
import dto.AddressDto;
import dto.CreateAddressDto;
import exception.ValidationException;
import lombok.NoArgsConstructor;
import mapper.address.AddressMapper;
import mapper.address.CreateAddressMapper;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import validator.CreateAddressValidator;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AddressService {
    private static final AddressService INSTANCE = new AddressService();
    private final AddressMapper addressMapper = AddressMapper.getInstance();
    private final AddressDao addressDao = AddressDao.getINSTANCE();
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final CreateAddressMapper createAddressMapper = CreateAddressMapper.getInstance();
    private final CreateAddressValidator createAddressValidator = CreateAddressValidator.getInstance();
    public AddressDto save(CreateAddressDto createAddressDto) {
        var validationResult = createAddressValidator.isValid(createAddressDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createAddressMapper.mapFrom(createAddressDto);
        return addressMapper.mapFrom(addressDao
                .find(sessionFactory, addressDao.save(sessionFactory, userEntity)).get());
    }

    public boolean update(CreateAddressDto createAddressDto) {
        return addressDao.update(sessionFactory, createAddressMapper.mapFrom(createAddressDto));
    }
    public boolean delete(Long id) {
        return addressDao.delete(id, sessionFactory);
    }

    public List<AddressDto> getAddresses(Long id) {
        return addressDao.findByUserId(sessionFactory, id)
                .stream().map(addressMapper::mapFrom).toList();
    }
    public static AddressService getInstance() {
        return INSTANCE;
    }
}
