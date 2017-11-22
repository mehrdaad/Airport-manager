package cz.fi.muni.pa165.service.impl;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.service.FlightService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the {@link FlightService}.
 *
 * @author Robert Duriancik
 */

@Service
public class FlightServiceImpl implements FlightService {

    @Inject
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
}
