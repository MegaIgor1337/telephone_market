package dao;

import entity.BaseEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>>
        implements Repository<K, E> {

    private final EntityManager entityManager;
    private final Class<E> clazz;

    @Override
    public E save(E entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public void delete(K id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(E entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<E> findById(K id) {
        entityManager.getTransaction().begin();
        Optional<E> entity = Optional.ofNullable(entityManager.find(clazz, id));
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public List<E> findAll() {
        entityManager.getTransaction().begin();
        var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        List<E> eList = entityManager.createQuery(criteria).getResultList();
        entityManager.getTransaction().commit();
        return eList;
    }
}