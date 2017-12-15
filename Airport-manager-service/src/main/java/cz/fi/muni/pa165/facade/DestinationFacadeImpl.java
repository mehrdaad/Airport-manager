package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.DestinationDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.service.DestinationService;
import cz.fi.muni.pa165.service.MappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the DestinationFacade interface. 
 *
 * @author Ondřej Přikryl
 */
@Service
@Transactional
public class DestinationFacadeImpl implements DestinationFacade{

    @Inject
    private DestinationService destinationService;

    @Inject
    private MappingService mappingService;

    @Override
    public Long createDestination(String country, String city) {
        return destinationService.createDestination(country, city);
    }

    @Override
    public void removeDestination(Long id) {
        destinationService.removeDestination(destinationService.getDestinationById(id));
    }

    @Override
    public void updateDestination(DestinationDTO destination) {
        destinationService.updateDestination(mappingService.mapTo(destination, Destination.class));
    }

    @Override
    public DestinationDTO getDestinationById(Long id) {
        Destination destinationById = destinationService.getDestinationById(id);
        return (destinationById == null) ? null : mappingService.mapTo(destinationById, DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getDestinationsByCountry(String country) {
        List<Destination> destinationsByCountry = destinationService.getDestinationsByCountry(country);
        return destinationsByCountry == null ? null : mappingService.mapTo(destinationsByCountry, DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getDestinationsByCity(String city) {
        List<Destination> destinationsByCity = destinationService.getDestinationsByCity(city);
        return destinationsByCity == null ? null : mappingService.mapTo(destinationsByCity, DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getAllDestinations() {
        List<Destination> allDestinations = destinationService.getAllDestinations();
        return allDestinations == null ? null : mappingService.mapTo(destinationService.getAllDestinations(), DestinationDTO.class);
    }

    @Override
    public List<FlightDTO> getAllIncomingFlights(DestinationDTO destination) {
        List<Flight> allIncomingFlights = destinationService.getAllIncomingFlights(
                mappingService.mapTo(destination, Destination.class));
        return allIncomingFlights == null ? null : mappingService.mapTo(allIncomingFlights, FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getAllOutgoingFlights(DestinationDTO destination) {
        List<Flight> allOutgoingFlights = destinationService.getAllOutgoingFlights(
                mappingService.mapTo(destination, Destination.class));
        return allOutgoingFlights == null ? null : mappingService.mapTo(allOutgoingFlights, FlightDTO.class);
    }
}
