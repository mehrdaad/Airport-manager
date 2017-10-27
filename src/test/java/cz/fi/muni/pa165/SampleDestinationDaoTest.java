package cz.fi.muni.pa165;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class SampleDestinationDaoTest extends BaseDaoTest {

    @Autowired
    private DestinationDao destinationDao;

    @PersistenceContext
    private EntityManager em; // same as in the dao implementation

    @Test
    @Transactional
    public void transactionalTest() throws Exception {
        String city = "city";
        String country = "country";


        Destination destination = new Destination();
        destination.setCity(city);
        destination.setCountry(country);

        destinationDao.create(destination);

        List<Destination> destinations = em.createQuery("select d from Destination d", Destination.class).getResultList();

        Assert.assertEquals(1, destinations.size());
        Assert.assertEquals(city, destinations.get(0).getCity());
        Assert.assertEquals(country, destinations.get(0).getCountry());
    }

    @Test
    public void nonTransactionalTest() throws Exception {
        List<Destination> destinations = destinationDao.findAll();

        Assert.assertEquals(0, destinations.size());
    }
}
