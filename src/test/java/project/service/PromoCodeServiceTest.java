package project.service;

import lombok.RequiredArgsConstructor;
import market.service.dto.CreatePromoCodeDto;
import market.service.dto.PromoCodeFilter;
import market.model.enums.PromoCodeStatus;
import market.model.repository.PromoCodeRepository;
import market.service.PromoCodeService;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@IT
@RequiredArgsConstructor
class PromoCodeServiceTest {
    private final PromoCodeService promoCodeService;
    private final PromoCodeRepository promoCodeRepository;

    @Test
    void getPromoCodes() {
        var filter = PromoCodeFilter.builder()
                .name("FIRST").build();
        var result = promoCodeService.getPromoCodes(filter, 0);
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("FIRST");
    }

    @Test
    void getPromoCodeWithPage() {
        var result = promoCodeService.getPromoCodeWithPage(2L, 0);
        assertThat(result.getProducts()).hasSize(4);
    }

    @Test
    void deleteProductFromPromoCode() {
        promoCodeService.deleteProductFromPromoCode("1", 1L);
        assertThat(promoCodeRepository.findById(1L).get().getProducts()).hasSize(3);
    }

    @Test
    void deletePromoCode() {
        promoCodeService.deletePromoCode(2L);
        assertThat(promoCodeRepository.findAll()).hasSize(1);
    }

    @Test
    void changeName() {
        promoCodeService.changeName(1L, "KKKKKK");
        assertThat(promoCodeRepository.findById(1L).get().getName())
                .isEqualTo("KKKKKK");
    }

    @Test
    void changeDiscount() {
        promoCodeService.changeDiscount(1L, "14.0");
        assertThat(promoCodeRepository.findById(1L).get().getDiscount())
                .isEqualTo(14.0);
    }

    @Test
    void changeStatus() {
        promoCodeService.changeStatus(1L, "CANCELED");
        assertThat(promoCodeRepository.findById(1L).get().getStatus())
                .isEqualTo(PromoCodeStatus.CANCELED);
    }

    @Test
    void addPromoCode() {
        var createPromoCodeDto = CreatePromoCodeDto.builder()
                .name("FSSSSA")
                .discount(15.00)
                .productsId(List.of(1L, 2L, 4L))
                .build();
        promoCodeService.addPromoCode(createPromoCodeDto);
        assertThat(promoCodeRepository.findAll()).hasSize(3);
    }
}