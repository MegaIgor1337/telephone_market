package service;

import dao.AddressDao;
import dto.address.AddressDto;
import dto.address.CreateAddressDto;
import dto.user.UserDto;
import entity.user.Gender;
import exception.ValidationException;
import lombok.NoArgsConstructor;
import mapper.address.AddressMapper;
import mapper.address.CreateAddressMapper;
import validator.CreateAddressValidator;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AddressService {
    private static final AddressService INSTANCE = new AddressService();
    private final AddressMapper addressMapper = AddressMapper.getInstance();
    private final AddressDao addressDao = AddressDao.getInstance();
    private final CreateAddressMapper createAddressMapper = CreateAddressMapper.getInstance();
    private final CreateAddressValidator createAddressValidator = CreateAddressValidator.getInstance();
    public AddressDto save(CreateAddressDto createAddressDto) {
        var validationResult = createAddressValidator.isValid(createAddressDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createAddressMapper.mapFrom(createAddressDto);
        return addressMapper.mapFrom(addressDao.save(userEntity));
    }

    public boolean update(CreateAddressDto createAddressDto) {
        return addressDao.update(createAddressMapper.mapFrom(createAddressDto));
    }
    public boolean delete(Long id) {
        return addressDao.delete(id);
    }

    public List<AddressDto> getAddresses(Long id) {
        return addressDao.getAddressesByUserId(id)
                .stream().map(addressMapper::mapFrom).toList();
    }
    public static AddressService getInstance() {
        return INSTANCE;
    }
}
