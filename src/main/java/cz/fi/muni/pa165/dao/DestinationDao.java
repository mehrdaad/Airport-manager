package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entities.Destination;

import java.util.List;

/**
 * Data Access Object layer for the {@link Destination} entity.
 *
 * @author Robert Duriancik
 */

public interface DestinationDao {

    /**
     * Save a {@link Destination} entity instance in the persistence storage.
     *
     * @param destination the {@link Destination} entity instance
     */
    void create(Destination destination);

    /**
     * Remove the {@link Destination} entity instance from the persistence storage.
     *
     * @param destination the {@link Destination} entity instance
     */
    void delete(Destination destination);

    /**
     * Update data of the {@link Destination} entity instance in the persistence storage
     *
     * @param destination the {@link Destination} entity instance
     */
    void update(Destination destination);

    /**
     * Find all instances of the {@link Destination} entity in the persistence storage.
     *
     * @return a list of {@link Destination} entities
     */
    List<Destination> findAll();

    /**
     * Find a {@link Destination} entity instance with specified <b>id</b>.
     * If the entity instance is contained in the persistence storage, it is returned from there.
     *
     * @param id the id of the {@link Destination} entity
     * @return the found {@link Destination} instance or null if the entity does not exist
     */
    Destination findById(Long id);

}
