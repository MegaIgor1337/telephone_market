package dao;

import entity.PromoCodeProduct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

public class PromoCodeProductRepository extends RepositoryBase<Long, PromoCodeProduct> {


    public PromoCodeProductRepository(EntityManager entityManager) {
        super(entityManager, PromoCodeProduct.class);
    }

}
