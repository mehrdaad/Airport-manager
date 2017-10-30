package cz.fi.muni.pa165;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Base configuration class for dao testing.
 *
 * @author Robert Duriancik
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class BaseDaoTest extends AbstractTestNGSpringContextTests {
}
