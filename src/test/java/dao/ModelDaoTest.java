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

    public void testSave() {
        Model model = new Model(null, "e43");
        modelDao.save(model);
        assertEquals(modelDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(modelDao.findAll().size() - 1).getId(), model.getId());
    }

    public void testDelete() {
        assertTrue(modelDao.delete(modelDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(modelDao.findAll().size() - 1).getId()));
    }


}