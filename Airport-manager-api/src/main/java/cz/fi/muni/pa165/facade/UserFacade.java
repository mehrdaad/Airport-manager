package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserCreateDTO;
import cz.fi.muni.pa165.dto.UserDTO;

import java.util.List;

/**
 * An interface that defines the facade layer above the User entity.
 *
 * @author Robert Duriancik
 */
public interface UserFacade {

    /**
     * Creates a new user.
     *
     * @param userCreateDTO representation of the user
     * @param rawPassword   raw user's password
     * @return id of the newly created user
     */
    Long createUser(UserCreateDTO userCreateDTO, String rawPassword);

    /**
     * Removes the user.
     *
     * @param userId user's id
     */
    void deleteUser(Long userId);

    /**
     * Returns an user specified by id.
     *
     * @param userId user's id
     * @return user or null if there is no user with the specified id
     */
    UserDTO getUser(Long userId);

    /**
     * Returns all created users
     *
     * @return the list of UserDTO objects
     */
    List<UserDTO> getAllUsers();

    /**
     * Returns an uset specified by email.
     *
     * @param userEmail user's email
     * @return user or null if there is no user with specified email
     */
    UserDTO getUserByEmail(String userEmail);

    /**
     * Authenticates the user.
     *
     * @param userAuthenticateDTO DTO holding user's id and password hash
     * @return true if the hashed password matches the records, false otherwise
     */
    boolean authenticate(UserAuthenticateDTO userAuthenticateDTO);

    /**
     * Checks if the given user is admin.
     *
     * @param userDTO representation of the user
     * @return true if the user is admin, false otherwise
     */
    boolean isAdmin(UserDTO userDTO);

}
