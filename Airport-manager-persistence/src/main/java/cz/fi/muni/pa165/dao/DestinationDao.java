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
     * @throws NullPointerException     if destination is null
     * @throws IllegalArgumentException if destination is not valid
     */
    void addDestination(Destination destination);

    /**
     * Remove the {@link Destination} entity instance from the persistence storage.
     *
     * @param destination the {@link Destination} entity instance
     * @throws NullPointerException     if destination is null
     * @throws IllegalArgumentException if destination is not in persistence storage
     */
    void removeDestination(Destination destination);

    /**
     * Update data of the {@link Destination} entity instance in the persistence storage
     *
     * @param destination the {@link Destination} entity instance
     * @throws NullPointerException     if destination is null
     * @throws IllegalArgumentException if destination is not in persistence storage
     */
    void updateDestination(Destination destination);

    /**
     * Find all instances of the {@link Destination} entity in the persistence storage.
     *
     * @return a list of {@link Destination} entities
     */
    List<Destination> getAllDestinations();

    /**
     * Find a {@link Destination} entity instance with specified <b>id</b>.
     * If the entity instance is contained in the persistence storage, it is returned from there.
     *
     * @param id the id of the {@link Destination} entity
     * @return the found {@link Destination} instance or null if the entity does not exist
     */
    Destination getDestination(Long id);

    /**
     * Find a {@link Destination} entity instance by specified <b>city</b>.
     * If the entity instance is contained in the persistence storage, it is returned from there.
     *
     * @param city the string containing desired city.
     * @return a list of {@link Destination} entities
     */
    List<Destination> getDestinationsByCity(String city);

    /**
     * Find a {@link Destination} entity instance by specified <b>country</b>.
     * If the entity instance is contained in the persistence storage, it is returned from there.
     *
     * @param country the string containing desired country.
     * @return a list of {@link Destination} entities
     */
    List<Destination> getDestinationsByCountry(String country);
}
