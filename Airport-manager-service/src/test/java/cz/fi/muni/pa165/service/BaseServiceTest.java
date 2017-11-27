package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.ServiceConfig;
import org.hibernate.service.spi.ServiceException;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

/**
 * @author Robert Duriancik
 */
@ContextConfiguration(classes = {ServiceConfig.class})
public class BaseServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    /**
     * Init mocks.
     *
     * @throws ServiceException If init goes wrong.
     */
    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
}
