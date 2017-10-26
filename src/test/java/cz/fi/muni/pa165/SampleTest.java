package cz.fi.muni.pa165;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.junit.Assert.assertEquals;


@ContextConfiguration(classes = PersistenceConfig.class)
public class SampleTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit(name = "entityManagerFactory")
    private EntityManagerFactory emf;

    @Test
    public void sampleTest() throws Exception {
        String city = "city";
        String country = "country";


        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Destination destination = new Destination();
        destination.setCity(city);
        destination.setCountry(country);

        em.persist(destination);
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        List<Destination> destinations = em.createQuery("select d from Destination d", Destination.class).getResultList();

        assertEquals(1, destinations.size());
        assertEquals(city, destinations.get(0).getCity());
        assertEquals(country, destinations.get(0).getCountry());

        em.close();
    }
}
