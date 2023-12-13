package market.service.impl;

import market.service.dto.ICreateAddressDto;
import market.model.repository.AddressRepository;
import market.service.dto.AddressDto;
import market.service.dto.CreateAddressDto;
import market.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import market.service.mapper.AddressMapper;
import market.service.mapper.CreateAddressMapper;
import market.model.repository.OrderRepository;
import market.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import market.service.validator.CreateAddressValidator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static market.service.util.ConstantContainer.PAGE_SIZE;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final CreateAddressMapper createAddressMapper;
    private final CreateAddressValidator createAddressValidator;

    @Override
    public AddressDto save(CreateAddressDto createAddressDto) {
        validateAddressParams(createAddressDto);
        var addressEntity = createAddressMapper.createAddressDtoToAddress(createAddressDto);
        addressEntity.setDeleted(false);
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
        if (!orderRepository.findAllByAddressId(id).isEmpty()) {
            address.ifPresent(a -> a.setDeleted(true));
            address.ifPresent(addressRepository::save);
        } else {
            addressRepository.deleteById(id);
        }
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
