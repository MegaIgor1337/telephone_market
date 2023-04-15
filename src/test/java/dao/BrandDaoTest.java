package dao;

import entity.brand.Brand;
import dao.filter.BrandFilter;
import junit.framework.TestCase;

import java.util.List;

public class BrandDaoTest extends TestCase {
    private static final BrandDao brandDao = BrandDao.getInstance();

    public synchronized void testFindAll() {
        List<Brand> brands = brandDao.findAll();
        assertEquals(8, brands.size());
    }


    public void testUpdate() {
        assertTrue(brandDao.update(new Brand(7L, "Vivo+")));
    }

    public void testFindById() {
        if (brandDao.findById(4L).isPresent()) {
            assertEquals(new Brand(4L, "Huawei"), brandDao.findById(4L).get());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        BrandFilter brandFilter = new BrandFilter(3, 0, "Xiaomi");
        assertEquals(new Brand(5L, "Xiaomi"), brandDao.findAll(brandFilter).get(0));
    }


    public void testDelete() throws InterruptedException {

        assertTrue(brandDao.delete(brandDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(brandDao.findAll().size() - 1).getId()));
    }

    public void testSave() {
        Brand brand = new Brand(null, "Google");
        brandDao.save(brand);
        assertEquals(brandDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(brandDao.findAll().size() - 1).getId(), brand.getId());
    }
}