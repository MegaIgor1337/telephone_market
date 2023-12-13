package market.service;

import market.service.dto.AddressDto;
import market.service.dto.CreateAddressDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {
    void delete(Long id);
    Page<AddressDto> getAddressesByUserId(Long id, Integer page);
    List<AddressDto> getAddressesByUserId(Long userId);
    AddressDto save(CreateAddressDto createAddressDto);
}
