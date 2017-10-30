package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class testing AirplaneDao implementation.
 *
 * @author Ondrej Prikryl
 */
public class AirplaneDaoImplTest extends BaseDaoTest {

    @Autowired
    private AirplaneDao airplaneDao;

    @PersistenceContext
    private EntityManager em; // same as in the dao implementation

    @Test
    @Transactional
    public void createAirplaneTest() {

        Airplane defaultAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(20)
                .setName("Boeing")
                .setType("777")
                .build();

        // add airplane happy scenario
        airplaneDao.addAirplane(defaultAirplane);

        List<Airplane> list = airplaneDao.findAll();
        Assert.assertTrue(list.size() == 1);
        Assert.assertNotNull(list.get(0).getId());
        Assert.assertEquals(list.get(0).getCapacity(), defaultAirplane.getCapacity());
        Assert.assertEquals(list.get(0).getName(), defaultAirplane.getName());
        Assert.assertEquals(list.get(0).getType(), defaultAirplane.getType());

        Airplane negativeCapacityAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(-4)
                .setName("Boeing")
                .setType("737")
                .build();

        // add airplane with negative capacity
        Assert.assertThrows(PersistenceException.class, () -> airplaneDao.addAirplane(negativeCapacityAirplane));

        Airplane notNullIdAirplane = new AirplaneBuilder()
                .setId(20L)
                .setCapacity(-4)
                .setName("Boeing")
                .setType("737")
                .build();

        // add airplane with non null id
        Assert.assertThrows(PersistenceException.class, () -> airplaneDao.addAirplane(notNullIdAirplane));

        Airplane nullStringAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(10)
                .setName(null)
                .setType(null)
                .build();

        // add airplane with null string name and type
        Assert.assertThrows(PersistenceException.class, () -> airplaneDao.addAirplane(nullStringAirplane));
    }

    @Test
    @Transactional
    public void deleteAirplaneTest() {

        Airplane defaultAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(20)
                .setName("Boeing")
                .setType("777")
                .build();

        airplaneDao.addAirplane(defaultAirplane);

        Assert.assertNotNull(airplaneDao.findById(defaultAirplane.getId()));
        airplaneDao.deleteAirplane(defaultAirplane);
        Assert.assertNull(airplaneDao.findById(defaultAirplane.getId()));
    }

    @Test
    @Transactional
    public void updateAirplaneTest() {

        // update airplane happy scenario
        Airplane defaultAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(20)
                .setName("Boeing")
                .setType("777")
                .build();

        airplaneDao.addAirplane(defaultAirplane);
        Long id = defaultAirplane.getId();

        Airplane newAirplane = new AirplaneBuilder()
                .setId(id)
                .setCapacity(30)
                .setName("Thunderbolt")
                .setType("A-10")
                .build();

        airplaneDao.updateAirplane(newAirplane);

        Airplane airplane = airplaneDao.findById(id);
        Assert.assertNotNull(airplane);
        Assert.assertEquals(newAirplane, airplane);

        // update airplane null id
        Airplane nullIdAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(20)
                .setName("Boeing")
                .setType("777")
                .build();

        Assert.assertThrows(IllegalArgumentException.class, () -> airplaneDao.updateAirplane(nullIdAirplane));
    }

    @Test
    @Transactional
    public void findByIdTest() {

        Airplane defaultAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(20)
                .setName("Boeing")
                .setType("777")
                .build();

        Airplane correctAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(30)
                .setName("A-10")
                .setType("Thunderbolt")
                .build();

        airplaneDao.addAirplane(correctAirplane);
        airplaneDao.addAirplane(defaultAirplane);
        Long id = defaultAirplane.getId();

        Airplane planeFromDb = airplaneDao.findById(id);
        Assert.assertNotNull(planeFromDb);
        Assert.assertEquals(planeFromDb.getId(), defaultAirplane.getId());
        Assert.assertEquals(planeFromDb.getType(), defaultAirplane.getType());
        Assert.assertEquals(planeFromDb.getCapacity(), defaultAirplane.getCapacity());
        Assert.assertEquals(planeFromDb.getName(), defaultAirplane.getName());
    }

    @Test
    @Transactional
    public void findAllTest() {

        Airplane defaultAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(20)
                .setName("Boeing")
                .setType("777")
                .build();

        Airplane correctAirplane = new AirplaneBuilder()
                .setId(null)
                .setCapacity(30)
                .setName("A-10")
                .setType("Thunderbolt")
                .build();

        airplaneDao.addAirplane(correctAirplane);
        airplaneDao.addAirplane(defaultAirplane);

        List<Airplane> airplanes = new ArrayList<>();
        airplanes.add(correctAirplane);
        airplanes.add(defaultAirplane);

        List<Airplane> airplanesFromDb = airplaneDao.findAll();
        Assert.assertNotNull(airplanesFromDb);
        Assert.assertTrue(airplanesFromDb.size() == 2);
        Assert.assertTrue(airplanesFromDb.containsAll(airplanes));
    }
}