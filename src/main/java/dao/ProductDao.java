package dao;

import entity.product.Product;
import exception.DaoException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao implements Dao<Long, Product> {
    @Getter
    private static final ProductDao INSTANCE = new ProductDao();

    @Override
    public Class<Product> getEntityClass() {
        return Product.class;
    }
}
