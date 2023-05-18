package service;

import dao.AddressRepository;
import dto.AddressDto;
import dto.CreateAddressDto;
import exception.ValidationException;
import lombok.Getter;
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
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
   @Getter
    private static final AddressService INSTANCE = new AddressService();
    private final AddressMapper addressMapper = AddressMapper.getInstance();
    private final AddressRepository addressRepository = new AddressRepository(
            HibernateUtil.getSessionFromFactory(sessionFactory));
    private final CreateAddressMapper createAddressMapper = CreateAddressMapper.getINSTANCE();
    private final CreateAddressValidator createAddressValidator = CreateAddressValidator.getInstance();
    public AddressDto save(CreateAddressDto createAddressDto) {
        var validationResult = createAddressValidator.isValid(createAddressDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createAddressMapper.mapFrom(createAddressDto);
        return addressMapper.mapFrom(addressRepository.save(userEntity));
    }

    public void update(CreateAddressDto createAddressDto) {
        addressRepository.update(createAddressMapper.mapFrom(createAddressDto));
    }
    public void delete(Long id) {
         addressRepository.delete(id);
    }

    public List<AddressDto> getAddresses(Long id) {
        return addressRepository.findByUserId(id)
                .stream().map(addressMapper::mapFrom).toList();
    }

}
