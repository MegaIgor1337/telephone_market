package dao;

import entity.Brand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandDao implements Dao<Long, Brand> {
    @Getter
    private static final BrandDao INSTANCE = new BrandDao();

    @Override
    public Class<Brand> getEntityClass() {
        return Brand.class;
    }
}
