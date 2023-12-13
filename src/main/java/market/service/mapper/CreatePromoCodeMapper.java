package market.service.mapper;

import market.service.dto.CreatePromoCodeDto;
import market.model.entity.Product;
import market.model.entity.PromoCode;
import market.model.entity.PromoCodeProduct;
import market.model.enums.PromoCodeStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CreatePromoCodeMapper {
    public PromoCode createPromoCodeDtoToPromoCode(CreatePromoCodeDto createPromoCodeDto) {
        return PromoCode.builder()
                .name(createPromoCodeDto.getName())
                .discount(createPromoCodeDto.getDiscount())
                .status(PromoCodeStatus.ACTIVE)
                .build();
    }

    public List<PromoCodeProduct> createPromoCodeProduct(PromoCode promoCode, List<Product> products) {
        return products.stream().map(p -> PromoCodeProduct.builder()
                .product(p)
                .promoCode(promoCode)
                .dateOfBegin(LocalDateTime.now())
                .build()).toList();
    }
}
