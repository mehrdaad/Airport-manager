package cz.fi.muni.pa165;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


@ContextConfiguration(classes = PersistenceConfig.class)
public class SampleTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void sampleTest() throws Exception {
        EntityManager em = emf.createEntityManager();
        // Test
        em.close();
    }
}
