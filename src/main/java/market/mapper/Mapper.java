package market.mapper;

import org.hibernate.Session;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
