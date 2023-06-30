package market.mapper;

import market.dto.CreatePromoCodeDto;
import market.entity.Product;
import market.entity.PromoCode;
import market.entity.PromoCodeProduct;
import market.enums.PromoCodeStatus;
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
