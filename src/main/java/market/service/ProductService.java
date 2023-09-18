package market.service;

import market.dto.CreateProductDto;
import market.dto.ProductDto;
import market.dto.ProductFilter;
import market.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductDto> getProducts(Pageable pageable);
    List<ProductDto> getProductsByUserIdAndOrderStatus(Long id, OrderStatus orderStatus);
    List<ProductDto> getProductsByUserIdInFavourites(Long id);
    Page<ProductDto> getProductsByPredicates(ProductFilter filter, Integer page);
    void addCount(Long id, String count);
    void removeCount(Long id, String count);
    void deleteProduct(Long id);
    List<ProductDto> getProductsForAddingPromoCode(Long promoCodeId);
    List<ProductDto> getProducts();
    void addNewProduct(CreateProductDto createProductDto);

}
