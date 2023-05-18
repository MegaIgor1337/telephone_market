package dao;

import entity.Product;

import javax.persistence.EntityManager;

public class ProductRepository extends RepositoryBase<Long, Product> {

    public ProductRepository(EntityManager entityManager) {
        super(entityManager, Product.class);
    }
}
