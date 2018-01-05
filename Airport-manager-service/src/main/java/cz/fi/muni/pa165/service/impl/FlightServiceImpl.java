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
    public Long addFlight(Flight flight) {
        try {
            flightDao.addFlight(flight);
            return flight.getId();
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
        try {
            return flightDao.getFlight(id);
        } catch (Exception e) {
            throw new FlightDataAccessException("Exception while fetching flight with id " + id, e);
        }
    }

    @Override
    public List<Flight> getAllFlights() {
        try {
            return flightDao.getAllFlights();
        } catch (Exception e) {
            throw new FlightDataAccessException("Exception while fetching all flights.", e);
        }
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
    public List<Flight> getCurrentFlights(LocalDateTime now) {
        if (now == null) {
            throw new NullPointerException("now cannot be null");
        }

        List<Flight> flights = getAllFlights();
        List<Flight> currentFlighs = flights.stream()
                .filter(flight -> {
                    LocalDateTime arrivalTime = flight.getArrivalTime();
                    LocalDateTime departureTime = flight.getDepartureTime();
                    return departureTime.isBefore(now) && arrivalTime.isAfter(now);
                })
                .collect(Collectors.toList());

        return currentFlighs;
    }

    @Override
    public void addSteward(Flight flight, Steward steward) {
        if (flight == null || steward == null) {
            throw new NullPointerException("Flight or steward is null");
        }
        if (flight.getId() == null || steward.getId() == null) {
            throw new IllegalArgumentException("Id of flight or steward is not set");
        }
        if (flight.getStewards().contains(steward)) {
            throw new IllegalArgumentException("Steward: " + steward +
                    " already on the flight: " + flight);
        }

        flight.addSteward(steward);
        updateFlight(flight);
    }
}
