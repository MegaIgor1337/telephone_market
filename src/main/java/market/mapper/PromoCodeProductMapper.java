package market.mapper;

import market.dto.PromoCodeProductDto;
import market.entity.PromoCodeProduct;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = ProductMapper.class)
public interface PromoCodeProductMapper {
    PromoCodeProductDto promoCodeProductToPromoCodeProductDto(PromoCodeProduct product);
}
