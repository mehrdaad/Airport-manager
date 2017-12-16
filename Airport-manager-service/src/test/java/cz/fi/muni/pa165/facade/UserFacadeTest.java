package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.UserCreateDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.entities.User;
import cz.fi.muni.pa165.service.MappingService;
import cz.fi.muni.pa165.service.UserService;
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

public class UserFacadeTest extends BaseFacadeTest {

    @Mock
    private UserService userService;
    @Mock
    private MappingService mappingService;

    @Autowired
    @InjectMocks
    private UserFacadeImpl userFacade;

    private User user;
    private UserDTO userDTO;
    private Long userId;
    private String userEmail;
    private String userName;
    private String userSurname;
    private LocalDateTime userRegistered;
    private boolean userIsAdmin;

    @BeforeMethod
    public void prepareTestData() {
        userId = 1L;
        userEmail = "posledny@konecne.yes";
        userIsAdmin = true;
        userName = "Posledny";
        userSurname = "Konecne";
        userRegistered = LocalDateTime.now();

        user = new User();
        user.setEmail(userEmail);
        user.setName(userName);
        user.setSurname(userSurname);
        user.setAdmin(userIsAdmin);
        user.setRegistered(userRegistered);
        user.setId(userId);

        userDTO = new UserDTO();
        userDTO.setEmail(userEmail);
        userDTO.setName(userName);
        userDTO.setSurname(userSurname);
        userDTO.setAdmin(userIsAdmin);
        userDTO.setRegistered(userRegistered);
        userDTO.setId(userId);

        Mockito.reset(userService, mappingService);
    }

    @Test
    public void testCreateUser() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setAdmin(userIsAdmin);
        userCreateDTO.setEmail(userEmail);
        userCreateDTO.setName(userName);
        userCreateDTO.setSurname(userSurname);
        userCreateDTO.setRegistered(userRegistered);

        when(mappingService.mapTo(userCreateDTO, User.class)).thenReturn(user);

        Long returnedId = userFacade.createUser(userCreateDTO, "password");

        Assert.assertEquals(userId, returnedId);
        verify(userService).addUser(user, "password");
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userService.getUser(userId)).thenReturn(user);

        userFacade.deleteUser(userId);

        verify(userService).getUser(userId);
        verify(userService).deleteUser(user);
    }

    @Test
    public void testGetUser() throws Exception {
        when(userService.getUser(userId)).thenReturn(user);
        when(mappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO returnedUser = userFacade.getUser(userId);

        Assert.assertEquals(userDTO, returnedUser);
        verify(userService).getUser(userId);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));
        when(mappingService.mapTo(Collections.singletonList(user), UserDTO.class)).thenReturn(Collections.singletonList(userDTO));

        List<UserDTO> returnedUserd = userFacade.getAllUsers();

        Assert.assertEquals(1, returnedUserd.size());
        Assert.assertEquals(userDTO, returnedUserd.get(0));

        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        when(userService.getUserByEmail(userEmail)).thenReturn(user);
        when(mappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO returnedUser = userFacade.getUserByEmail(userEmail);

        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(userDTO, returnedUser);

        verify(userService).getUserByEmail(userEmail);
    }

    @Test
    public void testIsAdmin() throws Exception {
        when(mappingService.mapTo(userDTO, User.class)).thenReturn(user);
        when(userService.isAdmin(user)).thenReturn(userIsAdmin);

        boolean response = userFacade.isAdmin(userDTO);

        Assert.assertEquals(userIsAdmin, response);
        verify(userService).isAdmin(user);
    }
}
