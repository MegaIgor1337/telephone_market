package project.repository;


import lombok.RequiredArgsConstructor;
import market.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.IntegrationTestBase;

import static market.util.ConstantContainer.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
public class ProductRepositoryTest extends IntegrationTestBase {
    private final ProductRepository productRepository;

    @Test
    void testPage() {
        var pageable = PageRequest.of(1, 3, Sort.by(ID));
        var products = productRepository.findAllBy(pageable);
        assertFalse(products.isEmpty());
        assertThat(products).hasSize(2);

    }
}
