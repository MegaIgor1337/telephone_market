package dao;

import entity.color.Color;
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
public class ColorDao implements Dao<Long, Color> {
    @Getter
    private static final ColorDao INSTANCE = new ColorDao();

    @Override
    public Class<Color> getEntityClass() {
        return Color.class;
    }
}
