package dao;

import entity.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDao implements Dao<Long, Address> {
    @Getter
    private static final AddressDao INSTANCE = new AddressDao();

    public List<Address> findByUserId(SessionFactory sessionFactory, Long id) {
        try (
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
