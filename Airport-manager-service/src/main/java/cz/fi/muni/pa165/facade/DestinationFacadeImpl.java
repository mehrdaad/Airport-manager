package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.DestinationDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.entities.Destination;
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
        if (destinationById == null){
            return null;
        }
        return mappingService.mapTo(destinationService.getDestinationById(id), DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getDestinationsByCountry(String country) {
        return mappingService.mapTo(destinationService.getDestinationsByCountry(country), DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getDestinationsByCity(String city) {
        return mappingService.mapTo(destinationService.getDestinationsByCity(city), DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getAllDestinations() {
        return mappingService.mapTo(destinationService.getAllDestinations(), DestinationDTO.class);
    }

    @Override
    public List<FlightDTO> getAllIncomingFlights(DestinationDTO destination) {
        return mappingService.mapTo(destinationService.getAllIncomingFlights(
                mappingService.mapTo(destination, Destination.class)), FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getAllOutgoingFlights(DestinationDTO destination) {
        return mappingService.mapTo(destinationService.getAllOutgoingFlights(
                mappingService.mapTo(destination, Destination.class)), FlightDTO.class);
    }
}
