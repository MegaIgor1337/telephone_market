package market.service.mapper;


import lombok.RequiredArgsConstructor;
import market.service.dto.PromoCodeDto;
import market.service.dto.PromoCodeDtoWithPage;
import market.service.util.PageUtil;
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
