package cz.fi.muni.pa165.service.impl;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Flight;
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
        flightDao.addFlight(flight);
    }

    @Override
    public void deleteFlight(Flight flight) {
        flightDao.deleteFlight(flight);
    }

    @Override
    public void updateFlight(Flight flight) {
        flightDao.updateFlight(flight);
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
        if (flightDao.getFlight(flight.getId()) == null) {
            throw new IllegalArgumentException(); // TODO change exception
        }

        return Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
    }

    @Override
    public List<Flight> getFlightsLastMonth() {
        LocalDateTime lastMonthDateTime = LocalDateTime.now().minusMonths(1);

        List<Flight> flights = getAllFlights();
        List<Flight> lastMonthFlights = flights.stream()
                .filter(flight -> {

                    LocalDateTime arrivalTime = flight.getArrivalTime();
                    LocalDateTime departureTime = flight.getDepartureTime();
                    if (arrivalTime.isAfter(lastMonthDateTime) ||
                            departureTime.isAfter(lastMonthDateTime)) {
                        return true;
                    } else {
                        return false;
                    }

                })
                .collect(Collectors.toList());

        return lastMonthFlights;
    }

}
