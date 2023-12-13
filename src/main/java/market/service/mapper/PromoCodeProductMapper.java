package market.service.mapper;

import market.service.dto.PromoCodeProductDto;
import market.model.entity.PromoCodeProduct;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = ProductMapper.class)
public interface PromoCodeProductMapper {
    PromoCodeProductDto promoCodeProductToPromoCodeProductDto(PromoCodeProduct product);
}
