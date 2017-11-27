package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.ServiceConfig;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;

@ContextConfiguration(classes = ServiceConfig.class)
public class BaseFacadeTest {
    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
}
