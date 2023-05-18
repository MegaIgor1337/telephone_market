package dao;

import entity.OrderProduct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

public class OrderProductRepository extends RepositoryBase<Long, OrderProduct> {

    public OrderProductRepository(EntityManager entityManager) {
        super(entityManager, OrderProduct.class);
    }
}
