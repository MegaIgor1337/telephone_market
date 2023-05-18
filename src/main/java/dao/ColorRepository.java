package dao;

import entity.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

public class ColorRepository extends RepositoryBase<Long, Color> {

    public ColorRepository(EntityManager entityManager) {
        super(entityManager, Color.class);
    }
}
