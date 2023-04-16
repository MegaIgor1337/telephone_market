package dao;

import entity.model.Model;
import dao.filter.ModelFilter;
import junit.framework.TestCase;

public class ModelDaoTest extends TestCase {
    private static final ModelDao modelDao = ModelDao.getInstance();

    public void testUpdate() {
        Model model = new Model(39L, "40");
        assertTrue(modelDao.update(model));
    }

    public void testFindById() {
        if (modelDao.findById(36L).isPresent()) {
            assertEquals("X7", modelDao.findById(36L).get().getModel());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        ModelFilter filter = new ModelFilter(4, 0, "X");
        assertEquals(4, modelDao.findAll(filter).size());
    }


}