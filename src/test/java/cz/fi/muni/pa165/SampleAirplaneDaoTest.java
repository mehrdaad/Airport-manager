package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 *
 * @author Honza
 */
public class SampleAirplaneDaoTest extends BaseDaoTest {
    
    @Autowired
    private AirplaneDao airplaneDao;

    @PersistenceContext
    private EntityManager em; // same as in the dao implementation

    @Test
    @Transactional
    public void transactionalTest() throws Exception {
        String name = "name";
        String type = "type";
        int capacity = 10;


        Airplane airplane = new Airplane();
        airplane.setName(name);
        airplane.setType(type);
        airplane.setCapacity(capacity);

        airplaneDao.addAirplane(airplane);

        List<Airplane> airplanes = em.createQuery("select a from Airplane a", Airplane.class).getResultList();

        Assert.assertEquals(1, airplanes.size());
        Assert.assertEquals(name, airplanes.get(0).getName());
        Assert.assertEquals(type, airplanes.get(0).getType());
        Assert.assertEquals(capacity, airplanes.get(0).getCapacity());
    }

    @Test
    public void nonTransactionalTest() throws Exception {
        List<Airplane> airplanes = airplaneDao.findAll();

        Assert.assertEquals(0, airplanes.size());
    }
    
}
