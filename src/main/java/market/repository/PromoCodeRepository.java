package market.repository;

import market.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

}
