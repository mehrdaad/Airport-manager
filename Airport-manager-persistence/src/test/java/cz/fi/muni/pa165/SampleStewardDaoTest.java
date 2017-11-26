package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.StewardDao;
import cz.fi.muni.pa165.entities.Steward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Class testing StewardDao implementation.
 *
 * @author Robert Duriancik
 */
public class SampleStewardDaoTest extends BaseDaoTest {

    @Autowired
    private StewardDao stewardDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void testCreateSteward_shouldSaveSteward() throws Exception {
        String firstName = "Peter";
        String surname = "Griffin";


        Steward steward = new Steward();
        steward.setFirstName(firstName);
        steward.setSurname(surname);

        stewardDao.createSteward(steward);

        List<Steward> stewards = em.createQuery("select c from Steward c", Steward.class).getResultList();

        Assert.assertEquals(1, stewards.size());
        Assert.assertEquals(firstName, stewards.get(0).getFirstName());
        Assert.assertEquals(surname, stewards.get(0).getSurname());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    @Transactional
    public void testCreateInvalidSteward_shouldThrowPropertyValueException() throws Exception {
        Steward steward = new Steward();
        steward.setFirstName(null);
        steward.setSurname(null);
        stewardDao.createSteward(steward);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    @Transactional
    public void testNullSteward_shouldThrowIllegalArgumentException() throws Exception {
        stewardDao.createSteward(null);
    }

    @Test
    @Transactional
    public void testDeleteSteward_shouldRemoveStewardFromDb() throws Exception {
        Steward steward1 = new Steward();
        steward1.setFirstName("Jozef");
        steward1.setSurname("Dlhy");

        Steward steward2 = new Steward();
        steward2.setFirstName("Steward");
        steward2.setSurname("Senior");

        em.persist(steward1);
        em.persist(steward2);

        stewardDao.deleteSteward(steward2);

        List<Steward> stewards = em.createQuery("select c from Steward c", Steward.class).getResultList();
        Assert.assertEquals(1, stewards.size());
        Assert.assertEquals(steward1.getFirstName(), stewards.get(0).getFirstName());
        Assert.assertEquals(steward1.getSurname(), stewards.get(0).getSurname());

        stewardDao.deleteSteward(steward1);
        stewards = em.createQuery("select c from Steward c", Steward.class).getResultList();
        Assert.assertEquals(0, stewards.size());
    }

    @Test(expectedExceptions = {NullPointerException.class})
    @Transactional
    public void testDeleteNullSteward_shouldThrowIllegalArgumentException() throws Exception {
        stewardDao.deleteSteward(null);
    }

    @Test
    @Transactional
    public void testUpdateSteward_shouldUpdateStewardInDB() throws Exception {
        Steward steward = new Steward();
        steward.setFirstName("Jozef");
        steward.setSurname("Dlhy");

        stewardDao.createSteward(steward);

        Long stewardId = steward.getId();
        String updatedFirstName = "Milos";
        String updatedSurname = "Kratky";

        steward.setFirstName(updatedFirstName);
        steward.setSurname(updatedSurname);

        stewardDao.updateSteward(steward);

        List<Steward> stewards = em.createQuery("select c from Steward c", Steward.class).getResultList();
        Assert.assertEquals(1, stewards.size());
        Assert.assertEquals(updatedFirstName, stewards.get(0).getFirstName());
        Assert.assertEquals(updatedSurname, stewards.get(0).getSurname());
        Assert.assertEquals(stewardId, stewards.get(0).getId());
    }

    @Test(expectedExceptions = {NullPointerException.class})
    @Transactional
    public void testUpdateNullSteward_shouldThrowNullPointerException() throws Exception {
        stewardDao.updateSteward(null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    @Transactional
    public void testUpdateNotSavedSteward_shouldThrowIllegalArgumentException() throws Exception {
        Steward steward = new Steward();
        steward.setFirstName("Jozef");
        steward.setSurname("Dlhy");

        stewardDao.updateSteward(steward);
    }

    @Test
    @Transactional
    public void testGetSteward_shouldReturnSteward() throws Exception {
        Steward steward = new Steward();
        steward.setFirstName("Jozef");
        steward.setSurname("Dlhy");

        em.persist(steward);

        Steward returnedSteward = stewardDao.getSteward(steward.getId());

        Assert.assertEquals(steward.getFirstName(), returnedSteward.getFirstName());
        Assert.assertEquals(steward.getSurname(), returnedSteward.getSurname());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testGetNullSteward_shouldThrowIllegalArgumentException() throws Exception {
        stewardDao.getSteward(null);
    }

    @Test
    public void testGetInvalidIdSteward_shouldReturnNull() throws Exception {
        Steward steward = stewardDao.getSteward(5012L);
        Assert.assertNull(steward);
    }

    @Test
    @Transactional
    public void testListAllStewards_shouldReturnAllStewards() throws Exception {
        Steward steward1 = new Steward();
        steward1.setFirstName("Jozef");
        steward1.setSurname("Dlhy");

        Steward steward2 = new Steward();
        steward2.setFirstName("Steward");
        steward2.setSurname("Senior");

        em.persist(steward1);
        em.persist(steward2);

        List<Steward> stewards = stewardDao.listAllStewards();

        Assert.assertEquals(2, stewards.size());
        Assert.assertTrue(stewards.contains(steward1));
        Assert.assertTrue(stewards.contains(steward2));
    }
}
