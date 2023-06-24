package project.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import market.ApplicationRunner;
import market.dto.CreateAddressDto;
import market.dto.CreateUpdateAddressDto;
import market.repository.AddressRepository;
import market.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class AddressServiceTest {

    private final Long id = 1L;
    private final AddressService addressService;

    @Test
    void save() {
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
    void update() {
        var createUpdateAddressDto = CreateUpdateAddressDto.builder()
                .id("1")
                .country("Bengladesh")
                .street("dslds")
                .house("sds")
                .city("dsld")
                .userId("1")
                .build();
        addressService.update(createUpdateAddressDto);
        var result = addressService.getAddresses(id, 2);
        assertThat(result).hasSize(2);
    }

    @Test
    void delete() {
        addressService.delete(id);
        var result = addressService.getAddresses(id, 2);
        assertThat(result).hasSize(0);
    }

    @Test
    void getAddresses() {
        var result = addressService.getAddresses(id, 2);
        assertThat(result).hasSize(1);
    }
}
