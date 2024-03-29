package project.repository;


import lombok.RequiredArgsConstructor;
import market.model.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.annotation.IT;

import static market.service.util.ConstantContainer.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


@RequiredArgsConstructor
@IT
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
