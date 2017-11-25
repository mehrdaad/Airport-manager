package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;

import java.util.List;

/**
 *
 * @author Ondřej Přikryl
 * @date 24.11.2017
 */
public interface DestinationService {

    public void createDestination(String country, String city);

    public void removeDestination(Destination destination);

    public void updateDestination(Destination destination);

    public Destination getDestinationById(Long id);

    public List<Destination> getDestinationsByCountry(String country);

    public List<Destination> getDestinationsByCity(String city);

    public Destination getDestinationByPosition(String country, String city);

    public List<Destination> getAllDestinations();

    public List<Flight> getAllIncomingFlights(Destination destination);

    public List<Flight> getAllOutgoingFlights(Destination destination);
}
