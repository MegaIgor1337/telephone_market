package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E, F> {
    boolean update(E e);

    Optional<E> findById(K id);

    List<E> findAll();

    List<E> findAll(F filter);

    boolean delete(K id);

    E save(E e);
}

