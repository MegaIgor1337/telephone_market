package dao;

import entity.Country;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

public class CountryRepository extends RepositoryBase<Long, Country> {

    public CountryRepository(EntityManager entityManager) {
        super(entityManager, Country.class);
    }
}
