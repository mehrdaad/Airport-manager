package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

public class UserDaoTest extends BaseDaoTest {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

    private User user;
    private String userEmail;

    @BeforeMethod
    public void initUser() {
        String userName = "Lao";
        String userSurname = "Tzun";
        userEmail = "layo@tzun.co";
        String passwordHash = "12351ds1f5d31fd6351f35d1";
        LocalDateTime registered = LocalDateTime.now().minusMonths(1);
        boolean isAdmin = false;

        user = createUser(
                passwordHash,
                userEmail,
                userName,
                userSurname,
                registered,
                isAdmin
        );
    }

    @Test
    @Transactional
    public void testCreateUser() throws Exception {
        userDao.addUser(user);

        List<User> users = em.createQuery("select u from User u", User.class)
                .getResultList();

        Assert.assertEquals(1, users.size());
        Assert.assertEquals(user, users.get(0));
    }

    @Test
    @Transactional
    public void testDeleteUser() throws Exception {
        em.persist(user);

        userDao.removeUser(user);

        List<User> users = em.createQuery("select u from User u", User.class)
                .getResultList();

        Assert.assertEquals(0, users.size());
    }

    @Test
    @Transactional
    public void testUpdateUser() throws Exception {
        em.persist(user);

        Long id = user.getId();

        String updateEmail = "tzunko@lao.soc";
        boolean updateAdmin = true;
        user.setEmail(updateEmail);
        user.setAdmin(updateAdmin);

        userDao.updateUser(user);

        List<User> users = em.createQuery("select u from User u", User.class)
                .getResultList();

        Assert.assertEquals(1, users.size());
        Assert.assertEquals(id, users.get(0).getId());
        Assert.assertEquals(updateEmail, users.get(0).getEmail());
        Assert.assertEquals(updateAdmin, users.get(0).isAdmin());
    }

    @Test
    @Transactional
    public void testGetUser() throws Exception {
        em.persist(user);

        User returnedUser = userDao.getUser(user.getId());

        Assert.assertEquals(user, returnedUser);
    }

    @Test
    @Transactional
    public void testGetAllUsers() throws Exception {
        em.persist(user);

        List<User> returnedUsers = userDao.getAllUsers();

        Assert.assertEquals(1, returnedUsers.size());
        Assert.assertEquals(user, returnedUsers.get(0));
    }

    @Test
    @Transactional
    public void testGetUserByEmail() throws Exception {
        em.persist(user);

        User returnedUser = userDao.getUserByEmail(userEmail);

        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user, returnedUser);
    }

    private User createUser(
            String passwordHash,
            String email,
            String name,
            String surname,
            LocalDateTime registered,
            boolean isAdmin
    ) {
        User user = new User();
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setRegistered(registered);
        user.setAdmin(isAdmin);

        return user;
    }
}
