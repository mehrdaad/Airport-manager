package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightCreateDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.dto.StewardDTO;

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
     */
    void createFlight(FlightCreateDTO flightCreateDTO);

    /**
     * Returns the list of all flights in which the steward participated.
     *
     * @param stewardDTO representation of the steward
     * @return the list of FlightDTO objects
     */
    List<FlightDTO> getFlightsBySteward(StewardDTO stewardDTO);

    /**
     * Returns the list of all flights
     *
     * @param destinationDTO representation of the destination
     * @return the list of FlightDTO objects
     */
    List<FlightDTO> getFlightsByDestination(DestinationDTO destinationDTO);

    /**
     * Returns the list of all flights with the specific airplane.
     *
     * @param airplaneDTO representation of the airplane
     * @return the list of FlightDTO objects
     */
    List<FlightDTO> getFlightsByAirplane(AirplaneDTO airplaneDTO);

    /**
     * Returns a flight specified by id.
     *
     * @param id id of the flight
     * @return flight or null if there is no flight with the specified id
     */
    FlightDTO getFlight(Long id);

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
     * Returns the duration of the flight represented by {@link Duration} object.
     *
     * @param flightDTO representation of the flight
     * @return duration of the flight
     */
    Duration getFlightTime(FlightDTO flightDTO);

    /**
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
