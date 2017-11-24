package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightCreateDTO;
import cz.fi.muni.pa165.dto.FlightDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Robert Duriancik
 */
public interface FlightFacade {

    void createFlight(FlightCreateDTO flightCreateDTO);

    List<FlightDTO> getFlightsBySteward(StewardDTO stewardDTO);

    List<FlightDTO> getFlightsByDestination(DestinationDTO destinationDTO);

    List<FlightDTO> getFlightsByAirplane(AirplaneDTO airplaneDTO);

    FlightDTO getFlight(Long id);

    List<FlightDTO> getAllFlights();

    List<FlightDTO> getFlightsSince(LocalDateTime sinceDateTime);

    Duration getFlightTime(FlightDTO flight);

    void deleteFlight(Long flightId);

    void changeArrivalTime(Long flightId, LocalDateTime arrivalTime);

    void addSteward(Long flightId, Long stewardId);

    void changeAirplane(Long flightId, Long airplaneId);
}
