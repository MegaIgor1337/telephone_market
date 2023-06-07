package project.repository;


import lombok.RequiredArgsConstructor;
import market.ApplicationRunner;
import market.entity.Address;
import market.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(classes = {ApplicationRunner.class, TestApplicationRunner.class})
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
public class AddressRepositoryTest {

    private final AddressRepository addressRepository;
    @Test
    public void testAddressesFindByUserId() {
        List<Address> addresses = addressRepository.findByUserId(1L);
        assertThat(1).isEqualTo(addresses.size());
    }
}
