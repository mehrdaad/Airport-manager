package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entities.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest extends BaseServiceTest {
    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    private User user;
    private Long userId;
    private String userEmail;
    private boolean userIsAdmin;

    @BeforeMethod
    public void prepareTestData() {
        userId = 1L;
        userEmail = "neviem@uz.com";
        userIsAdmin = false;

        user = new User();
        user.setId(userId);
        user.setEmail(userEmail);
        user.setAdmin(userIsAdmin);
        user.setName("Neviem");
        user.setSurname("Uz");
        user.setRegistered(LocalDateTime.now());

        Mockito.reset(userDao);
    }

    @Test
    public void testAddUser() throws Exception {
        userService.addUser(user, "password");

        Assert.assertNotNull(user.getPasswordHash());
        verify(userDao).addUser(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        userService.deleteUser(user);

        verify(userDao).removeUser(user);
    }

    @Test
    public void testUpdateUser() throws Exception {
        userService.updateUser(user);

        verify(userDao).updateUser(user);
    }

    @Test
    public void testGetUser() throws Exception {
        when(userDao.getUser(userId)).thenReturn(user);

        User returnedUser = userService.getUser(userId);

        Assert.assertEquals(user, returnedUser);
        verify(userDao).getUser(userId);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userDao.getAllUsers()).thenReturn(Collections.singletonList(user));

        List<User> returnedUsers = userService.getAllUsers();

        Assert.assertEquals(Collections.singletonList(user), returnedUsers);
        verify(userDao).getAllUsers();
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        when(userDao.getUserByEmail(userEmail)).thenReturn(user);

        User returnedUser = userService.getUserByEmail(userEmail);

        Assert.assertEquals(user, returnedUser);
        verify(userDao).getUserByEmail(userEmail);
    }

    @Test
    public void testIsAdmin() throws Exception {
        when(userDao.getUser(userId)).thenReturn(user);

        boolean response = userService.isAdmin(user);

        Assert.assertEquals(userIsAdmin, response);
        verify(userDao).getUser(userId);
    }
}
