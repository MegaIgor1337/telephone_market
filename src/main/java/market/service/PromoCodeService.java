package market.service;

import market.service.dto.CreatePromoCodeDto;
import market.service.dto.PromoCodeDto;
import market.service.dto.PromoCodeDtoWithPage;
import market.service.dto.PromoCodeFilter;
import org.springframework.data.domain.Page;

public interface PromoCodeService {
    Page<PromoCodeDto> getPromoCodes(PromoCodeFilter promoCodeFilter, Integer page);
    PromoCodeDtoWithPage getPromoCodeWithPage(Long id, Integer page);
    void deleteProductFromPromoCode(String productId, Long promoCodeId);
    void deletePromoCode(Long id);
    void changeName(Long id, String newName);
    void changeDiscount(Long id, String newDiscount);
    void changeStatus(Long id, String newStatus);
    void addPromoCode(CreatePromoCodeDto createPromoCodeDto);

}
