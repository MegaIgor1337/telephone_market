package dao;

import entity.Model;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelDao implements Dao<Long, Model> {
    @Getter
    private static final ModelDao INSTANCE = new ModelDao();


    @Override
    public Class<Model> getEntityClass() {
        return Model.class;
    }
}
