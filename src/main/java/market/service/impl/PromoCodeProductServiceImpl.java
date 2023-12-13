package market.service.impl;

import lombok.RequiredArgsConstructor;
import market.model.entity.PromoCodeProduct;
import market.model.repository.ProductRepository;
import market.model.repository.PromoCodeProductRepository;
import market.model.repository.PromoCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromoCodeProductServiceImpl implements market.service.PromoCodeProductService {
    private final PromoCodeProductRepository promoCodeProductRepository;
    private final ProductRepository productRepository;
    private final PromoCodeRepository promoCodeRepository;

    @Override
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
