package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entities.User;

import java.util.List;

/**
 * Data Access Object layer for the {@link User} entity.
 *
 * @author Robert Duriancik
 */
public interface UserDao {

    /**
     * Save a {@link User} entity instance in the persistence storage.
     *
     * @param user the {@link User} entity instance
     * @throws NullPointerException     if user is null
     * @throws IllegalArgumentException if user is not valid
     */
    void addUser(User user);

    /**
     * Remove the {@link User} entity instance from the persistence storage.
     *
     * @param user the {@link User} entity instance
     * @throws NullPointerException     if user is null
     * @throws IllegalArgumentException if user is not in persistence storage
     */
    void removeUser(User user);

    /**
     * Update data of the {@link User} entity instance in the persistence storage
     *
     * @param user the {@link User} entity instance
     * @throws NullPointerException     if user is null
     * @throws IllegalArgumentException if user is not in persistence storage
     */
    void updateUser(User user);

    /**
     * Find all instances of the {@link User} entity in the persistence storage.
     *
     * @return a list of {@link User} entities
     */
    List<User> getAllUsers();

    /**
     * Find a {@link User} entity instance with specified <b>id</b>.
     * If the entity instance is contained in the persistence storage, it is returned from there.
     *
     * @param id the id of the {@link User} entity
     * @return the found {@link User} instance or null if the entity does not exist
     * @throws NullPointerException if id is null
     */
    User getUser(Long id);

    /**
     * Find a {@link User} entity instance with specified <b>email</b>.
     *
     * @param email user's email
     * @return the found {@link User} instance or null if the entity does not exist
     */
    User getUserByEmail(String email);
}
