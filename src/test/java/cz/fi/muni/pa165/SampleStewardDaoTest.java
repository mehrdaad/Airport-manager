package cz.fi.muni.pa165;

import cz.fi.muni.pa165.Dao.StewardDao;
import cz.fi.muni.pa165.Entity.Steward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class SampleStewardDaoTest extends BaseDaoTest {

    @Autowired
    private StewardDao stewardDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void transactionalTest() throws Exception {
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

    @Test
    public void nonTransactionalTest() throws Exception {
        List<Steward> stewards = stewardDao.listAllStewards();

        Assert.assertEquals(0, stewards.size());
    }
}
