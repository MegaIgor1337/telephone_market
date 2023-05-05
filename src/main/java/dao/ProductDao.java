package dao;

import entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao implements Dao<Long, Product> {
    @Getter
    private static final ProductDao INSTANCE = new ProductDao();

    @Override
    public Class<Product> getEntityClass() {
        return Product.class;
    }
}
