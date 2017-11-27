package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.DestinationDTO;
import cz.fi.muni.pa165.dto.FlightDTO;

import java.util.List;

/**
 * Facade interface of Destination
 *
 * @author Ondřej Přikryl
 */
public interface DestinationFacade {

    /**
     * Create destination with given parameters.
     *
     * @param country string containing name of country.
     * @param city string containing name of city.
     * @return Id of created destination.
     */
    Long createDestination(String country, String city);

    /**
     * Removes destination by given id.
     *
     * @param id of the destination we wanna remove.
     */
    void removeDestination(Long id);

    /**
     * Update destination.
     *
     * @param destination we want to update.
     */
    void updateDestination(DestinationDTO destination);

    /**
     * Get destination by its id.
     *
     * @param id of the destination we want to retrieve.
     * @return DestinationDTO instance.
     */
    DestinationDTO getDestinationById(Long id);

    /**
     * Find all destinations by country.
     *
     * @param country String specifying country name.
     * @return List of all destinations in specified country in db.
     */
    List<DestinationDTO> getDestinationsByCountry(String country);

    /**
     * Find all destinations by city.
     *
     * @param city String with name of the city.
     * @return List of all destinations named "city" in db.
     */
    List<DestinationDTO> getDestinationsByCity(String city);

    /**
     * Find all destinations.
     *
     * @return List of all destinations in db.
     */
    List<DestinationDTO> getAllDestinations();

    /**
     * Find all incoming flights in this destination.
     *
     * @param destination of interest.
     * @return List of FlightDTO containing flights.
     */
    List<FlightDTO> getAllIncomingFlights(DestinationDTO destination);

    /**
     * Find all outgoing flights in this destination.
     *
     * @param destination of interest.
     * @return List of FlightDTO containing flights.
     */
    List<FlightDTO> getAllOutgoingFlights(DestinationDTO destination);
}
