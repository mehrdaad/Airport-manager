package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.DestinationDTO;
import cz.fi.muni.pa165.dto.FlightDTO;

import java.util.List;

/**
 *
 * @author Ondřej Přikryl
 * @date 24. 11. 2017
 */
public interface DestinationFacade {

    public void createDestination(String country, String city);

    public void removeDestination(Long id);

    public void updateDestination(DestinationDTO destination);

    public DestinationDTO getDestinationById(Long id);

    public DestinationDTO getDestinationByPosition(String country, String city);

    public List<DestinationDTO> getDestinationsByCountry(String country);

    public List<DestinationDTO> getDestinationsByCity(String city);

    public List<DestinationDTO> getAllDestinations();

    public List<FlightDTO> getAllIncomingFlights(DestinationDTO destination);

    public List<FlightDTO> getAllOutgoingFlights(DestinationDTO destination);
}
