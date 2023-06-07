package market.repository;

import market.entity.PromoCodeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PromoCodeProductRepository extends JpaRepository<PromoCodeProduct, Long> {

}
