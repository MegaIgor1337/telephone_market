package project.service;

import lombok.RequiredArgsConstructor;
import market.dto.ProductFilter;
import market.enums.OrderStatus;
import market.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
@IT
@RequiredArgsConstructor
public class ProductServiceTest {
    private final ProductService productService;

    private final Long userId = 1L;
    @Test
    void getProducts() {
        var pageable = PageRequest.of(0, 2);
        var result = productService.getProducts(pageable);
        assertThat(result).hasSize(2);
    }

    @Test
    void getProductsByUserIdAndOrderStatus() {
        var result = productService.getProductsByUserIdAndOrderStatus(userId, OrderStatus.DELIVERED);
        assertThat(result).hasSize(2);
    }

    @Test
    void getProductsByUserIdInFavourites() {
        var result = productService.getProductsByUserIdInFavourites(userId);
        assertThat(result).hasSize(1);
    }

    @Test
    void getProductsByPredicates() {
        var createUserProductDto = ProductFilter.builder()
                .country("Germany").build();
            var result = productService.getProductsByPredicates(createUserProductDto, 0);
            assertThat(result).hasSize(2);
    }
}
