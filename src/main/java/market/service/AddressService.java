package market.service;

import market.dto.CreateUpdateAddressDto;
import market.dto.ICreateAddressDto;
import market.entity.Address;
import market.mapper.CreateUpdateAddressDtoMapper;
import market.repository.AddressRepository;
import market.dto.AddressDto;
import market.dto.CreateAddressDto;
import market.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import market.mapper.AddressMapper;
import market.mapper.CreateAddressMapper;
import org.springframework.stereotype.Service;
import market.validator.CreateAddressValidator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final CreateUpdateAddressDtoMapper createUpdateAddressDtoMapper;
    private final CreateAddressMapper createAddressMapper;
    private final CreateAddressValidator createAddressValidator;
    public AddressDto save(CreateAddressDto createAddressDto) {
        validateAddressParams(createAddressDto);
        var addressEntity = createAddressMapper.createAddressDtoToAddress(createAddressDto);
        return addressMapper.mapFrom(addressRepository.save(addressEntity));
    }

    private void validateAddressParams(ICreateAddressDto createAddressDto) {
        var validationResult = createAddressValidator.isValid(createAddressDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
    }

    public void update(CreateUpdateAddressDto createUpdateAddressDto) {
        validateAddressParams(createUpdateAddressDto);
        Address address = createUpdateAddressDtoMapper
                .CreateUpdateAddressDtoToAddress(createUpdateAddressDto);
        addressRepository.save(address);
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
