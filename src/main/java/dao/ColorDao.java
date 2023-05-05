package dao;

import entity.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColorDao implements Dao<Long, Color> {
    @Getter
    private static final ColorDao INSTANCE = new ColorDao();

    @Override
    public Class<Color> getEntityClass() {
        return Color.class;
    }
}
