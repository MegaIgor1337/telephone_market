package market.model.repository;

import market.model.entity.PromoCode;
import market.model.entity.PromoCodeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoCodeProductRepository extends JpaRepository<PromoCodeProduct, Long> {
    List<PromoCodeProduct> findAllByPromoCode(PromoCode promoCode);
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM PromoCodeProduct pc  " +
           "WHERE pc.product.id = :productId and pc.promoCode.id = :promoCodeId")
    void deleteAllByProductIdAndPromoCodeId(Long productId, Long promoCodeId);
}
