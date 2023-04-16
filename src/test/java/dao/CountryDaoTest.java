package dao;

import entity.country.Country;
import dao.filter.CountryFilter;
import junit.framework.TestCase;

public class CountryDaoTest extends TestCase {

    private static final CountryDao countryDao = CountryDao.getInstance();

    public void testUpdate() {
        Country country = new Country(17L, "Kongo");
        assertTrue(countryDao.update(country));
    }

    public void testFindById() {
        if (countryDao.findById(8L).isPresent()) {
            assertEquals("USA", countryDao.findById(8L).get().getCountry());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        CountryFilter filter = new CountryFilter(2, 0, "Rus");
        assertEquals(1, countryDao.findAll(filter).size());
    }


}