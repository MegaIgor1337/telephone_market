package dao;

import entity.country.Country;
import filter.CountryFilter;
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

    public void testSave() {
        Country country = new Country(null, "Niggeria");
        country = countryDao.save(country);
        assertEquals(countryDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(countryDao.findAll().size() - 1).getId(), country.getId());
    }

    public void testDelete() {
        assertTrue(countryDao.delete(countryDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(countryDao.findAll().size() - 1).getId()));
    }


}