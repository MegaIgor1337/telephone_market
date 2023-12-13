package market.service.mapper;

import market.service.dto.ProductDto;
import market.model.entity.Product;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {ColorMapper.class, CountryMapper.class, BrandMapper.class, ModelMapper.class})
public interface ProductMapper {
    Product productDtoToProduct(ProductDto productDto);
    ProductDto productToProductDto(Product product);
}
