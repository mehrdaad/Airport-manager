package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.Flight;
import org.springframework.stereotype.Service;

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

}
