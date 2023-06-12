package project.repository;


import lombok.RequiredArgsConstructor;
import market.ApplicationRunner;
import market.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;

import static market.util.StringContainer.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = {ApplicationRunner.class, TestApplicationRunner.class})
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
@Rollback
public class ProductRepositoryTest {
    private final ProductRepository productRepository;

    @Test
    void testPage() {
        var pageable = PageRequest.of(1, 3, Sort.by(ID));
        var products = productRepository.findAllBy(pageable);
        assertFalse(products.isEmpty());
        assertThat(products).hasSize(2);

    }
}
