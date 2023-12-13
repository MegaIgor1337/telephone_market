package project.service;

import lombok.RequiredArgsConstructor;
import market.service.dto.CreateProductDto;
import market.service.dto.ProductFilter;
import market.model.enums.OrderStatus;
import market.model.repository.ProductRepository;
import market.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;
@IT
@RequiredArgsConstructor
public class ProductServiceTest  {
    private final ProductService productService;
    private final ProductRepository productRepository;
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

    @Test
    void addNewProduct() {
        var createProductDto = CreateProductDto.builder()
                .brand("Xiaomi")
                .model("Redmi 6")
                .color("Black")
                .country("USA")
                .count(12)
                .cost(42.5).build();
        productService.addNewProduct(createProductDto);
        var result = productService.getProducts();
        assertThat(result).hasSize(6);
    }


    @Test
    void removeCount() {
        productService.removeCount(5L, "20");
        assertThat(productRepository.findById(5L).get()
                .getStorageCount()).isEqualTo(50);
    }

    @Test
    void addCount() {
        productService.addCount(5L, "20");
        assertThat(productRepository.findById(5L).get()
                .getStorageCount()).isEqualTo(90);
    }

}
