package market.repository;

import market.entity.PromoCode;
import market.entity.PromoCodeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoCodeProductRepository extends JpaRepository<PromoCodeProduct, Long> {
    List<PromoCodeProduct> findAllByPromoCode(PromoCode promoCode);
    @Modifying(clearAutomatically = true)
    void deleteAllByProductIdAndPromoCodeId(Long productId, Long promoCodeId);
}
