package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * An interface that defines a service access to the {@link Flight} entity.
 *
 * @author Robert Duriancik
 */

@Service
public interface FlightService {
    /**
     * Saves Save a {@link Flight} entity instance in the persistence storage.
     *
     * @param flight the {@link Flight} entity instance
     * @throws cz.fi.muni.pa165.exceptions.FlightDataAccessException in case of any exception on a DAO layer
     */
    void addFlight(Flight flight);

    /**
     * Removes a {@link Flight} entity instance from the persistence storage.
     *
     * @param flight the {@link Flight} entity instance
     * @throws cz.fi.muni.pa165.exceptions.FlightDataAccessException in case of any exception on a DAO layer
     */
    void deleteFlight(Flight flight);

    /**
     * Update data of the {@link Flight} entity instance in the persistence storage
     *
     * @param flight the {@link Flight} entity instance
     * @throws cz.fi.muni.pa165.exceptions.FlightDataAccessException in case of any exception on a DAO layer
     */
    void updateFlight(Flight flight);

    /**
     * Find a {@link Flight} entity instance with specified <b>id</b>.
     *
     * @param id id of the {@link Flight} entity
     * @return {@link Flight} instance or null if the entity does not exist.
     */
    Flight getFlight(Long id);

    /**
     * Find all instances of the {@link Flight} entity in the persistence storage.
     *
     * @return list of {@link Flight} entities
     */
    List<Flight> getAllFlights();

    /**
     * Returns a duration of the flight.
     *
     * @param flight the {@link Flight} entity instance
     * @return duration of the flight represented by a {@link Duration} object
     * @throws IllegalArgumentException if the flight id is null
     * @throws NullPointerException if the flight is null
     */
    Duration getFlightTime(Flight flight);

    /**
     * Find all instances of the {@link Flight} entity in the persistence storage
     * whose arrivalTime or departureTime was after sinceDateTime.
     *
     * @param sinceDateTime the date since when to find flights
     * @return list of {@link Flight} entities since sinceDateTime
     * @throws NullPointerException if the sinceDateTime parameter is null
     */
    List<Flight> getFlightsSince(LocalDateTime sinceDateTime);

    /**
     * Adds steward to the flight.
     *
     * @param flight  the {@link Flight} entity instance
     * @param steward the {@link Steward} entity instance
     * @throws IllegalArgumentException if the steward is already on the flight
     * @throws NullPointerException if flight or steward is null
     */
    void addSteward(Flight flight, Steward steward);
}
