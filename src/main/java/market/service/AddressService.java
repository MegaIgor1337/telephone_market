package market.service;

import market.repository.AddressRepository;
import market.dto.AddressDto;
import market.dto.CreateAddressDto;
import market.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import market.mapper.address.AddressMapper;
import market.mapper.address.CreateAddressMapper;
import org.springframework.stereotype.Service;
import market.validator.CreateAddressValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final CreateAddressMapper createAddressMapper;
    private final CreateAddressValidator createAddressValidator;
    public AddressDto save(CreateAddressDto createAddressDto) {
        var validationResult = createAddressValidator.isValid(createAddressDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createAddressMapper.mapFrom(createAddressDto);
        return addressMapper.mapFrom(addressRepository.save(userEntity));
    }

    public void update(CreateAddressDto createAddressDto) {
        addressRepository.save(createAddressMapper.mapFrom(createAddressDto));
    }
    public void delete(Long id) {
        if (addressRepository.findById(id).isPresent()) {
            addressRepository.delete(addressRepository.findById(id).get());
        }
    }

    public List<AddressDto> getAddresses(Long id) {
        return addressRepository.findByUserId(id)
                .stream().map(addressMapper::mapFrom).collect(Collectors.toList());
    }

}
