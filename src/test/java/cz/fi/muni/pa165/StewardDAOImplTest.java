package cz.fi.muni.pa165;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.junit.Assert.*;

@ContextConfiguration(classes = PersistenceConfig.class)
public class StewardDAOImplTest extends AbstractTestNGSpringContextTests {

    private static Steward steward;
    private StewardDAO dao = new StewardDAOImpl();

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void sampleTest() throws Exception {

        dao.createSteward(steward);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Steward temp = em.find(Steward.class, steward.getId());
        assertEquals(steward, temp);
    }

    static {
        String firtName = "John";
        String surname = "Lost";

        steward = new Steward();
        steward.setFirstName(firtName);
        steward.setSurname(surname);
    }
}
