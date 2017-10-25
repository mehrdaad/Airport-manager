package cz.fi.muni.pa165;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


@ContextConfiguration(classes = PersistenceConfig.class)
public class SampleTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void sampleTest() throws Exception {
        // Test
    }
}
