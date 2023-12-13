package project.repository;


import lombok.RequiredArgsConstructor;
import market.model.entity.Address;
import market.model.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@IT
public class AddressRepositoryTest  {

    private final AddressRepository addressRepository;
   @Test
    public void testAddressesFindByUserId() {
        Page<Address> addresses = addressRepository.findByUserIdAndDeletedFalse(1L, PageRequest.of(0, 2));
        assertThat(addresses).hasSize(1);
   }
}
