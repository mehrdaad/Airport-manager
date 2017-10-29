package cz.fi.muni.pa165.Dao;


import cz.fi.muni.pa165.Entity.Destination;
import cz.fi.muni.pa165.Entity.Flight;

import java.util.List;

/**
 * Data Access Object layer for the {@link Flight} entity.
 *
 * @author Karel Jiranek
 */
public interface FlightDao {

    /**
     * Save a {@link Flight} entity instance in the persistence storage.
     * @param flight the {@link Flight} entity instance
     */
    void create(Flight flight);

    /**
     * Remove a {@link Flight} entity instance from the persistence storage.
     * @param flight the {@link Flight} entity instance
     */
    void delete(Flight flight);

    /**
     * Update data of the {@link Destination} entity instance in the persistence storage
     * @param destination the {@link Destination} entity instance
     */
    void update(Flight flight);

    /**
     * Find all instances of the {@link Flight} entity in the persistence storage.
     * @return List of {@link Flight} entities
     */
    List<Flight> findAll();

    /**
     * Find a {@link Flight} entity instance with specified <b>id</b>.
     * @param id Id of the {@link Flight} entity
     * @return {@link Flight} instance or null if the entity does not exist.
     */
    Flight findById(Long id);
}
