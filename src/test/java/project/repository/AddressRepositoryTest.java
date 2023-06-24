package project.repository;


import lombok.RequiredArgsConstructor;
import market.ApplicationRunner;
import market.entity.Address;
import market.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;
import project.annotation.IT;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class AddressRepositoryTest {

    private final AddressRepository addressRepository;
   @Test
    public void testAddressesFindByUserId() {
        Page<Address> addresses = addressRepository.findByUserId(1L, PageRequest.of(2, 2));
        assertThat(addresses).hasSize(1);
   }
}
