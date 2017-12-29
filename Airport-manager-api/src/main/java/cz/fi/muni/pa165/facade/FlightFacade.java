package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightCreateDTO;
import cz.fi.muni.pa165.dto.FlightDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * An interface that defines the facade layer above the Flight entity.
 *
 * @author Robert Duriancik
 */
public interface FlightFacade {

    /**
     * Creates a new flight.
     *
     * @param flightCreateDTO representation of the flight
     * @return id of the newly created flight
     */
    Long createFlight(FlightCreateDTO flightCreateDTO);

    /**
     * Returns a flight specified by id.
     *
     * @param id id of the flight
     * @return flight or null if there is no flight with the specified id
     */
    FlightDTO getFlight(Long id);

    /**
     * Update flight.
     *
     * @param flightDTO Flight to be updated.
     */
    void updateFlight(FlightDTO flightDTO);

    /**
     * Returns all created flights.
     *
     * @return the list of FlightDTO objects
     */
    List<FlightDTO> getAllFlights();

    /**
     * Returns the list of flights since the specific date.
     *
     * @param sinceDateTime the date since when to find flights
     * @return the list of FlightDTO objects
     */
    List<FlightDTO> getFlightsSince(LocalDateTime sinceDateTime);

    /**
     * Returns the list of current flights.
     *
     * @param now the current date
     * @return the list of FlightDTO objects
     */
    List<FlightDTO> getCurrentFlights(LocalDateTime now);

    /**
     * Returns the duration of the flight represented by {@link Duration} object.
     *
     * @param flightDTO representation of the flight
     * @return duration of the flight
     */
    Duration getFlightTime(FlightDTO flightDTO);

    /**
     * Removes flight.
     *
     * @param flightId id of the flight
     */
    void deleteFlight(Long flightId);

    /**
     * Changes flight arrival time.
     *
     * @param flightId    id of the flight
     * @param arrivalTime time when the flight arrived
     */
    void changeArrivalTime(Long flightId, LocalDateTime arrivalTime);

    /**
     * Adds the steward to the flight.
     *
     * @param flightId  id of the flight
     * @param stewardId id of the steward
     */
    void addStewardToFlight(Long flightId, Long stewardId);

    /**
     * Changes the airplane of the flight.
     *
     * @param flightId   id of the flight
     * @param airplaneId id of the airplane
     */
    void changeAirplane(Long flightId, Long airplaneId);
}
