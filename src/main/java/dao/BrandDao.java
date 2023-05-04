package dao;

import entity.brand.Brand;
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
public class BrandDao implements Dao<Long, Brand> {
    @Getter
    private static final BrandDao INSTANCE = new BrandDao();

    @Override
    public Class<Brand> getEntityClass() {
        return Brand.class;
    }
}
