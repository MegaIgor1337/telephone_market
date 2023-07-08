package market.mapper;

import market.dto.PromoCodeDto;
import market.entity.PromoCode;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = PromoCodeProductMapper.class)
public interface PromoCodeMapper {
    PromoCodeDto promoCodeTopromoCodeDto(PromoCode promoCode);
}
