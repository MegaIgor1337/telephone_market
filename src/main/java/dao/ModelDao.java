package dao;

import exception.DaoException;
import entity.model.Model;
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
public class ModelDao implements Dao<Long, Model> {
    @Getter
    private static final ModelDao INSTANCE = new ModelDao();


    @Override
    public Class<Model> getEntityClass() {
        return Model.class;
    }
}
