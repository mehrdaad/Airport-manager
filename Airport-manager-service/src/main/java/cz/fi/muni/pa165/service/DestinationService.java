package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.exceptions.DestinationDataAccessException;

import java.util.List;

/**
 * Interface defining destination service methods.
 *
 * @author Ondřej Přikryl
 * @date 24.11.2017
 */
public interface DestinationService {

    /**
     * Creates destination.
     *
     * @param country String containing name of the country.
     * @param city String containing name of the city.
     * @throws DestinationDataAccessException if fail in DAO occurs.
     * @return Long id of created destination.
     */
    public Long createDestination(String country, String city);

    /**
     * Removes destination from the db.
     *
     * @param destination Destination to be removed.
     * @throws DestinationDataAccessException if fail in DAO occurs.
     */
    public void removeDestination(Destination destination);

    /**
     * Updates destination in the db.
     *
     * @param destination Destination to be updated.
     * @throws DestinationDataAccessException if fail in DAO occurs.
     */
    public void updateDestination(Destination destination);

    /**
     * Gets destination by its ID.
     *
     * @param id Long containing id of the desired destination.
     * @throws DestinationDataAccessException if fail in DAO occurs.
     * @return Destination instance if destination is is db, null otherwise.
     */
    public Destination getDestinationById(Long id);

    /**
     * Filter destinations by country name.
     *
     * @param country String containing name of the country.
     * @throws DestinationDataAccessException if fail in DAO occurs.
     * @return List of Destination, may be empty.
     */
    public List<Destination> getDestinationsByCountry(String country);

    /**
     * Filter destinations by city name.
     *
     * @param city String containing name of the city.
     * @throws DestinationDataAccessException if fail in DAO occurs.
     * @return List of Destination, may be empty.
     */
    public List<Destination> getDestinationsByCity(String city);

    /**
     * Gets list of all destinations currently in db.
     *
     * @return List of Destination, all in db.
     */
    public List<Destination> getAllDestinations();

    /**
     * Gets all incoming flights in given destination.
     *
     * @param destination Destination of interest.
     * @throws DestinationDataAccessException if fail in DAO occurs.
     * @return List of Flight, all incoming flights towards this destination.
     */
    public List<Flight> getAllIncomingFlights(Destination destination);

    /**
     * Gets all outgoing flights in given destination.
     *
     * @param destination Destination of interest.
     * @throws DestinationDataAccessException if fail in DAO occurs.
     * @return List of Flight, all outgoing flights from this destination.
     */
    public List<Flight> getAllOutgoingFlights(Destination destination);
}
