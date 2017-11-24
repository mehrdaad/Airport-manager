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
    void addFlight(Flight flight);

    void deleteFlight(Flight flight);

    void updateFlight(Flight flight);

    Flight getFlight(Long id);

    List<Flight> getAllFlights();

    Duration getFlightTime(Flight flight);

    List<Flight> getFlightsSince(LocalDateTime sinceDateTime);

    void addSteward(Flight flight, Steward steward);
}
