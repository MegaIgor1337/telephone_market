package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Dao<K extends Serializable, E> {
    default List<E> get(SessionFactory sessionFactory) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<E> eList = session.createQuery("FROM " + getEntityClass().getName(), getEntityClass()).list();
            session.getTransaction().commit();
            return eList;
        }
    }

    default Optional<E> find(SessionFactory sessionFactory, K k) {

        try (
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            E e = session.find(getEntityClass(), k);
            session.getTransaction().commit();
            return Optional.ofNullable(e);
        }
    }

    default boolean delete(K k, SessionFactory sessionFactory) {
        try (
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<E> e = find(sessionFactory, k);
            session.delete(e.get());
            session.getTransaction().commit();
            return true;
        }
    }

    default boolean update(SessionFactory sessionFactory, E e) {
        try (
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(e);
            session.getTransaction().commit();
            return true;
        }
    }

    default K save(SessionFactory sessionFactory, E e) {
        try (
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Serializable id = session.save(e);
            session.getTransaction().commit();
            return (K) id;
        }
    }

    Class<E> getEntityClass();
}

