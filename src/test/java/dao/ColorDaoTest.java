package dao;

import entity.color.Color;
import filter.ColorFilter;
import junit.framework.TestCase;

public class ColorDaoTest extends TestCase {
    private static final ColorDao colorDao = ColorDao.getInstance();

    public synchronized void testFindAll() {
        assertEquals(10, colorDao.findAll().size());
    }

    public void testUpdate() {
        Color color = new Color(6L, "Blue-Green");
        assertTrue(colorDao.update(color));
    }

    public void testFindById() {
        if (colorDao.findById(4L).isPresent()) {
            assertEquals("Gold", colorDao.findById(4L).get().getColor());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        ColorFilter colorFilter = new ColorFilter(2, 0, "Blue");
        assertEquals(1, colorDao.findAll(colorFilter).size());
    }

    public void testSave() {
        Color color = new Color(null, "Gray");
        colorDao.save(color);
        assertEquals(colorDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(colorDao.findAll().size() - 1).getId(), color.getId());
    }

    public void testDelete() {
        assertTrue(colorDao.delete(colorDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(colorDao.findAll().size() - 1).getId()));
    }


}