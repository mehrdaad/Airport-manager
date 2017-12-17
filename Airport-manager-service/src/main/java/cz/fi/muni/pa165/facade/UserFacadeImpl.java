package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserCreateDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.entities.User;
import cz.fi.muni.pa165.service.MappingService;
import cz.fi.muni.pa165.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the {@link UserFacade} interface.
 *
 * @author Robert Duriancik
 */

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private MappingService mappingService;

    @Override
    public Long createUser(UserCreateDTO userCreateDTO, String rawPassword) {
        User mappedUser = mappingService.mapTo(userCreateDTO, User.class);
        userService.addUser(mappedUser, rawPassword);
        return mappedUser.getId();
    }

    @Override
    public void deleteUser(Long userId) {
        userService.deleteUser(userService.getUser(userId));
    }

    @Override
    public UserDTO getUser(Long userId) {
        User user = userService.getUser(userId);
        return user == null ? null : mappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        return user == null ? null : mappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        return userService.authenticate(
                userService.getUser(userAuthenticateDTO.getUserId()),
                userAuthenticateDTO.getPassword());
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        return userService.isAdmin(mappingService.mapTo(userDTO, User.class));
    }
}
