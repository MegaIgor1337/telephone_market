package dao;

import entity.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressRepository extends RepositoryBase<Long, Address> implements AddressDao {

    EntityManager entityManager;

    public AddressRepository(EntityManager entityManager) {
        super(entityManager, Address.class);
        this.entityManager = entityManager;
    }

    public List<Address> findByUserId(Long id) {
        entityManager.getTransaction().begin();
        List<Address> addresses = entityManager.createQuery("FROM Address where user.id = :id ", Address.class)
                .setParameter("id", id)
                .getResultList();
        entityManager.getTransaction().commit();
        return  addresses;
    }


}
