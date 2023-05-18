package dao;

import entity.Brand;
import entity.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

public class BrandRepository extends RepositoryBase<Long, Brand> {

    public BrandRepository(EntityManager entityManager) {
        super(entityManager, Brand.class);
    }
}
