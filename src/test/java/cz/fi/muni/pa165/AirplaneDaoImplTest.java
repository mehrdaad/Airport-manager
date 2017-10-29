package cz.fi.muni.pa165;

import cz.fi.muni.pa165.Dao.AirplaneDao;
import cz.fi.muni.pa165.Entity.Airplane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.AssertTrue;
import java.util.List;


/**
 * Class testing AirplaneDao implementation.
 *
 * @author Ondrej Prikryl
 */
public class AirplaneDaoImplTest extends BaseDaoTest {

    Airplane defaultAirplane;
    Airplane negativeCapacityAirplane;
    Airplane notNullIdAirplane;

    @Autowired
    private AirplaneDao airplaneDao;

    @PersistenceContext
    private EntityManager em; // same as in the dao implementation

    @BeforeClass
    private void createAirplanes() {
        defaultAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(20)
                .setName("Boeing")
                .setType("777")
                .build();

        negativeCapacityAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(-4)
                .setName("Boeing")
                .setType("737")
                .build();

        notNullIdAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(-4)
                .setName("Boeing")
                .setType("737")
                .build();
    }

    @Test
    @Transactional
    public void createAirplaneTest() {

        // add airplane happy scenario
        airplaneDao.addAirplane(defaultAirplane);

        List<Airplane> list = airplaneDao.findAll();
        Assert.assertTrue(list.size() == 1);
        Assert.assertNotNull(list.get(0).getId());
        Assert.assertEquals(list.get(0).getCapacity(), defaultAirplane.getCapacity());
        Assert.assertEquals(list.get(0).getName(), defaultAirplane.getName());
        Assert.assertEquals(list.get(0).getType(), defaultAirplane.getType());

        // add airplane with negative capacity
        airplaneDao.addAirplane(negativeCapacityAirplane);
        Assert.assertThrows(org.hibernate.exception.ConstraintViolationException.class, airplaneDao.addAirplane(negativeCapacityAirplane));
        Assert.

        // add with non null id
        airplaneDao.addAirplane(notNullIdAirplane);

    }

    @Test
    @Transactional
    public void transactionalTest() throws Exception {
        String name = "name";
        String type = "type";
        int capacity = 10;


        Airplane airplane = new Airplane();
        airplane.setId(new Long(20));
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
}
