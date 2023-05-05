package dao;

import entity.Country;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryDao implements Dao<Long, Country> {
    @Getter
    private static final CountryDao INSTANCE = new CountryDao();

    @Override
    public Class<Country> getEntityClass() {
        return Country.class;
    }
}
