package market.mapper;

import market.dto.ProductDto;
import market.entity.Product;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {ColorMapper.class, CountryMapper.class, BrandMapper.class, ModelMapper.class})
public interface ProductMapper {
    Product productDtoToProduct(ProductDto productDto);
    ProductDto productToProductDto(Product product);
}
