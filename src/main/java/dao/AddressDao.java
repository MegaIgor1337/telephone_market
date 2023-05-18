package dao;

import entity.Address;
import org.hibernate.SessionFactory;

import java.util.List;

public interface AddressDao {
    List<Address> findByUserId(Long id);
}
