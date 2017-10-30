package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.DestinationDao;
import cz.fi.muni.pa165.entities.Destination;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Cakl
 */
public class DestinationDaoTest extends BaseDaoTest{
    
    @Autowired
    private DestinationDao destinationDao;
    
    @PersistenceContext
    private EntityManager em;
    
    /**
    * Checks that findAll operation works fine.
    */
    @Test
    @Transactional
    public void findAll(){
        
        Destination  dest1 = new Destination();
        Destination  dest2 = new Destination();
        
        dest1.setCountry("country1");
        dest1.setCity("city1");
	dest2.setCountry("country2");
        dest2.setCity("city2");

        destinationDao.create(dest1);
	destinationDao.create(dest2);
        
        List<Destination> destinations = destinationDao.findAll();
        
	Assert.assertEquals(destinations.size(), 2);
	
	Destination dest1assert = new Destination();
	Destination dest2assert = new Destination();
	dest1assert.setCountry("country1");
        dest1assert.setCity("city1");
        dest2assert.setCountry("country2");
        dest2assert.setCity("city2");
		
        Assert.assertTrue(destinations.contains(dest1assert));
        Assert.assertTrue(destinations.contains(dest2assert));
	
    }
    
    /**
    * Checks that create operation works fine.
    */
    @Test
    @Transactional
    public void create(){
        Destination  dest = new Destination();
        
        dest.setCountry("country1");
        dest.setCity("city1");
        destinationDao.create(dest);
        
        List<Destination> destinations =  em.createQuery("select d from Destination d", Destination.class).getResultList();
        Assert.assertEquals(1, destinations.size());
        Assert.assertEquals(dest, destinations.get(0));
    
    }
    
    /**
    * Checks that update operation works fine.
    */
    @Test
    @Transactional
    public void update(){
        
        Destination  dest = new Destination();
        
        dest.setCountry("country1");
        dest.setCity("city1");
        destinationDao.create(dest);
        
        List<Destination> destinations = em.createQuery("select f from Destination f", Destination.class).getResultList();
        
        Assert.assertEquals("city1", destinations.get(0).getCity());
        Assert.assertEquals("country1", destinations.get(0).getCountry());
        
        dest.setCountry("updateCountry");
        destinationDao.update(dest);
        
        Assert.assertEquals("city1", destinations.get(0).getCity());
        Assert.assertEquals("updateCountry", destinations.get(0).getCountry());
    	
    }
    
    /**
    * Checks that null DAO object will return null for non existent ID and also that delete operation works fine + getId operation works.
    */
    @Test
    @Transactional
    public void delete(){
        
        Destination  dest = new Destination();
        
        dest.setCountry("country1");
        dest.setCity("city1");
        destinationDao.create(dest);
        
	Assert.assertNotNull(destinationDao.findById(dest.getId()));
	destinationDao.delete(dest);
	Assert.assertNull(destinationDao.findById(dest.getId()));
    }
    
}
