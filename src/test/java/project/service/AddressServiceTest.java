package project.service;

import lombok.RequiredArgsConstructor;
import market.service.dto.CreateAddressDto;
import market.service.dto.CreateUpdateAddressDto;
import market.model.repository.UserRepository;
import market.service.AddressService;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@IT
public class AddressServiceTest {

    private final Long id = 1L;
    private final UserRepository userRepository;

    private final AddressService addressService;

    @Test
    void save() {
        System.out.println("save ");
        System.out.println(userRepository.findAll());
        var createAddressDto = CreateAddressDto.builder()
                .country("Uganda")
                .house("123")
                .flat("4")
                .city("jjkk")
                .userId("1")
                .street("ogo")
                .build();
        var result = addressService.save(createAddressDto);
        assertThat(result.getId()).isEqualTo(3);
    }



    @Test
    void delete() {

        addressService.delete(id);
        var result = addressService.getAddressesByUserId(id, 2);
        assertThat(result).hasSize(0);
    }

    @Test
    void getAddresses() {

        var result = addressService.getAddressesByUserId(id, 1);
        assertThat(result).hasSize(0);
    }
}
