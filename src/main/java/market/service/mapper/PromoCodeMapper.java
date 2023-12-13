package market.service.mapper;

import market.service.dto.PromoCodeDto;
import market.model.entity.PromoCode;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = PromoCodeProductMapper.class)
public interface PromoCodeMapper {
    PromoCodeDto promoCodeTopromoCodeDto(PromoCode promoCode);
}
