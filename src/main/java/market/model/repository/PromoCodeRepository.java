package market.model.repository;

import market.model.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long>,
        JpaSpecificationExecutor<PromoCode> {
    Optional<PromoCode> findAllByName(String name);
    void deleteAllById(Long id);
}
