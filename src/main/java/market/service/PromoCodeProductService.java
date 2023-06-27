package market.service;

import lombok.RequiredArgsConstructor;
import market.entity.PromoCodeProduct;
import market.repository.ProductRepository;
import market.repository.PromoCodeProductRepository;
import market.repository.PromoCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromoCodeProductService {
    private final PromoCodeProductRepository promoCodeProductRepository;
    private final ProductRepository productRepository;
    private final PromoCodeRepository promoCodeRepository;

    @Transactional
    public void addProductToPromoCode(Long promoCodeId, Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var promoCode = promoCodeRepository.findById(promoCodeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var promoCodeProduct = PromoCodeProduct.builder()
                .product(product)
                .promoCode(promoCode)
                .dateOfBegin(LocalDateTime.now())
                .build();
        promoCodeProductRepository.saveAndFlush(promoCodeProduct);
    }
}
