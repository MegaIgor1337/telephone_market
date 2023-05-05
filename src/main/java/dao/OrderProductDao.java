package dao;

import entity.OrderProduct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProductDao implements Dao<Long, OrderProduct> {
    @Getter
    private static final OrderProductDao INSTANCE = new OrderProductDao();

    @Override
    public Class<OrderProduct> getEntityClass() {
        return OrderProduct.class;
    }
}
