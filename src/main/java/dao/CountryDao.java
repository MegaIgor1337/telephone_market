package dao;

import entity.country.Country;
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
public class CountryDao implements Dao<Long, Country> {
    @Getter
    private static final CountryDao INSTANCE = new CountryDao();

    @Override
    public Class<Country> getEntityClass() {
        return Country.class;
    }
}
