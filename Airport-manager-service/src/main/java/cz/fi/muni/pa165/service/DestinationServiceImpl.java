package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.DestinationDao;
import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DestinationServiceImpl implements DestinationService {

    @Autowired
    private DestinationDao destinationDao;

    @Autowired
    private FlightDao flightDao;

    @Override
    public void createDestination(String country, String city) {
        Destination destination = new Destination();
        destination.setCountry(country);
        destination.setCity(city);
        destinationDao.addDestination(destination);
    }

    @Override
    public void removeDestination(Destination destination) {
        destinationDao.removeDestination(destination);
    }

    @Override
    public void updateDestination(Destination destination) {
        destinationDao.updateDestination(destination);
    }

    @Override
    public Destination getDestinationById(Long id) {
        return destinationDao.getDestination(id);
    }

    @Override
    public List<Destination> getDestinationsByCountry(String country) {
        List<Destination> destinations = destinationDao.getAllDestinations();
        List<Destination> result = new ArrayList<>();
        for(Destination destination : destinations) {
            if(destination.getCountry().equals(country)) {
                result.add(destination);
            }
        }
        return result;
    }

    @Override
    public List<Destination> getDestinationsByCity(String city) {
        List<Destination> destinations = destinationDao.getAllDestinations();
        List<Destination> result = new ArrayList<>();
        for(Destination destination : destinations) {
            if(destination.getCity().equals(city)) {
                result.add(destination);
            }
        }
        return result;
    }

    @Override
    public Destination getDestinationByPosition(String country, String city) {
        List<Destination> destinations = destinationDao.getAllDestinations();

        for(Destination destination : destinations) {
            if(destination.getCountry().equals(country) && destination.getCity().equals(city)) {
                return destination;
            }
        }
        return null;
    }

    @Override
    public List<Destination> getAllDestinations() {
        return destinationDao.getAllDestinations();
    }

    @Override
    public List<Flight> getAllIncomingFlights(Destination destination) {
        List<Flight> flights = flightDao.getAllFlights();
        List<Flight> incoming = new ArrayList<>();
        for(Flight flight : flights) {
            if(flight.getArrivalLocation().equals(destination)) {
                incoming.add(flight);
            }
        }
        return incoming;
    }

    @Override
    public List<Flight> getAllOutgoingFlights(Destination destination) {
        List<Flight> flights = flightDao.getAllFlights();
        List<Flight> outgoing = new ArrayList<>();
        for(Flight flight : flights) {
            if(flight.getDepartureLocation().equals(destination)) {
                outgoing.add(flight);
            }
        }
        return outgoing;
    }
}
