package dao;

import entity.address.Address;
import entity.user.User;
import exception.DaoException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDao implements Dao<Long, Address> {
    @Getter
    private static final AddressDao INSTANCE = new AddressDao();

    public List<Address> findByUserId(Long id) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Address> eList = session.createQuery("FROM Address where user.id = " + id + " ",
                    getEntityClass()).list();
            session.getTransaction().commit();
            return eList;
        }
    }

    @Override
    public Class<Address> getEntityClass() {
        return Address.class;
    }
}
