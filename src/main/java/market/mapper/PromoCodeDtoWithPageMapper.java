package market.mapper;


import lombok.RequiredArgsConstructor;
import market.dto.PromoCodeDto;
import market.dto.PromoCodeDtoWithPage;
import market.util.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromoCodeDtoWithPageMapper {
    public PromoCodeDtoWithPage promoCodeDtoToPromoCodeDtoWithPage(PromoCodeDto promoCodeDto,
                                                               Integer pageNumber,
                                                               Integer pageSize) {
        var pages = PageUtil.createPageFromList(promoCodeDto.getProducts(), PageRequest.of(pageNumber, pageSize));
        return PromoCodeDtoWithPage.builder()
                .id(promoCodeDto.getId())
                .status(promoCodeDto.getStatus())
                .discount(promoCodeDto.getDiscount())
                .name(promoCodeDto.getName())
                .products(pages)
                .build();
    }
}
