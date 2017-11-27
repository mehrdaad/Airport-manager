package cz.fi.muni.pa165.service.impl;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.exceptions.FlightDataAccessException;
import cz.fi.muni.pa165.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link FlightService}.
 *
 * @author Robert Duriancik
 */

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightDao flightDao;

    @Override
    public void addFlight(Flight flight) {
        try {
            flightDao.addFlight(flight);
        } catch (Exception e) {
            throw new FlightDataAccessException("Exception while adding flight: " + flight, e);
        }
    }

    @Override
    public void deleteFlight(Flight flight) {
        try {
            flightDao.deleteFlight(flight);
        } catch (Exception e) {
            throw new FlightDataAccessException("Exception while deleting flight: " + flight, e);
        }
    }

    @Override
    public void updateFlight(Flight flight) {
        try {
            flightDao.updateFlight(flight);
        } catch (Exception e) {
            throw new FlightDataAccessException("Exception while updating flight: " + flight, e);
        }
    }

    @Override
    public Flight getFlight(Long id) {
        return flightDao.getFlight(id);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightDao.getAllFlights();
    }

    @Override
    public Duration getFlightTime(Flight flight) {
        if (flight == null) {
            throw new NullPointerException("Flight is null");
        }
        if (flightDao.getFlight(flight.getId()) == null) {
            throw new IllegalArgumentException("Flight id has to be set.");
        }

        return Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
    }

    @Override
    public List<Flight> getFlightsSince(LocalDateTime sinceDateTime) {
        if (sinceDateTime == null) {
            throw new NullPointerException("sinceDateTime cannot be null.");
        }

        List<Flight> flights = getAllFlights();
        List<Flight> flightsSinceDateTime = flights.stream()
                .filter(flight -> {
                    LocalDateTime arrivalTime = flight.getArrivalTime();
                    LocalDateTime departureTime = flight.getDepartureTime();
                    return arrivalTime.isAfter(sinceDateTime) ||
                            departureTime.isAfter(sinceDateTime);

                })
                .collect(Collectors.toList());

        return flightsSinceDateTime;
    }

    @Override
    public void addSteward(Flight flight, Steward steward) {
        if (flight.getStewards().contains(steward)) {
            throw new IllegalArgumentException("Steward: " + steward +
                    " already on the flight: " + flight);
        }

        flight.addSteward(steward);
    }
}
