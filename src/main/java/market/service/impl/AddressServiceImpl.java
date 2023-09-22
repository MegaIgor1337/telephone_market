package market.service.impl;

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
import market.service.AddressService;
import market.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import market.validator.CreateAddressValidator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static market.util.ConstantContainer.PAGE_SIZE;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final CreateAddressMapper createAddressMapper;
    private final CreateAddressValidator createAddressValidator;

    @Override
    public AddressDto save(CreateAddressDto createAddressDto) {
        validateAddressParams(createAddressDto);
        var addressEntity = createAddressMapper.createAddressDtoToAddress(createAddressDto);
        return addressMapper.addressToAddressDto(addressRepository.save(addressEntity));
    }

    private void validateAddressParams(ICreateAddressDto createAddressDto) {
        var validationResult = createAddressValidator.isValid(createAddressDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
    }


    @Override
    public void delete(Long id) {
        var address = addressRepository.findById(id);
        address.ifPresent(a -> a.setDeleted(true));
        address.ifPresent(addressRepository::save);
    }

    @Override
    public Page<AddressDto> getAddressesByUserId(Long id, Integer page) {
        return addressRepository.findByUserIdAndDeletedFalse(id, PageRequest.of(page, PAGE_SIZE))
                .map(addressMapper::addressToAddressDto);
    }
    @Override
    public List<AddressDto> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(addressMapper::addressToAddressDto).toList();
    }

}
