package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An interface that defines a service access to the {@link User} entity.
 *
 * @author Robert Duriancik
 */

@Service
public interface UserService {
    /**
     * Saves a {@link User} entity instance in the persistence storage.
     *
     * @param user        the {@link User} entity instance
     * @param rawPassword the unencrypted password
     * @throws cz.fi.muni.pa165.exceptions.UserDataAccessException in case of any exception on a DAO layer
     */
    void addUser(User user, String rawPassword);

    /**
     * Removes a {@link User} entity instance from the persistence storage.
     *
     * @param user the {@link User} entity instance
     * @throws cz.fi.muni.pa165.exceptions.UserDataAccessException in case of any exception on a DAO layer
     */
    void deleteUser(User user);

    /**
     * Update data of the {@link User} entity instance in the persistence storage
     *
     * @param user the {@link User} entity instance
     * @throws cz.fi.muni.pa165.exceptions.UserDataAccessException in case of any exception on a DAO layer
     */
    void updateUser(User user);

    /**
     * Find a {@link User} entity instance with specified <b>id</b>.
     *
     * @param id id of the {@link User} entity
     * @return {@link User} instance or null if the entity does not exist.
     */
    User getUser(Long id);

    /**
     * Find all instances of the {@link User} entity in the persistence storage.
     *
     * @return list of {@link User} entities
     */
    List<User> getAllUsers();

    /**
     * Find a {@link User} entity instance with specified <b>email</b>.
     *
     * @param email email of the {@link User} entity
     * @return {@link User} instance or null if the entity does not exist.
     */
    User getUserByEmail(String email);

    /**
     * Authenticates a user.
     *
     * @param user     the {@link User} entity instance
     * @param password hashed user's password
     * @return true only if the hashed password matches the records, false otherwise
     */
    boolean authenticate(User user, String password);

    /**
     * Checks if the given user is admin.
     *
     * @param user the {@link User} entity instance
     * @return true if the user is admin, false otherwise
     */
    boolean isAdmin(User user);
}
