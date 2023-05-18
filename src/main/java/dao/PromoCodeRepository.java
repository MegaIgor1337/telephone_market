package dao;

import entity.PromoCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

public class PromoCodeRepository extends RepositoryBase<Long, PromoCode>  {


    public PromoCodeRepository(EntityManager entityManager) {
        super(entityManager, PromoCode.class);
    }

}
