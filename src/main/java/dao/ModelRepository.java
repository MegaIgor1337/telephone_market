package dao;

import entity.Model;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

public class ModelRepository  extends RepositoryBase<Long, Model>{

    public ModelRepository(EntityManager entityManager) {
        super(entityManager, Model.class);
    }
}
