package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.DestinationDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.service.DestinationService;
import cz.fi.muni.pa165.service.MappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Karel Jiranek
 */
public class DestinationFacadeTest extends BaseFacadeTest {

    @Mock
    private MappingService mappingService;

    @Mock
    private DestinationDTO destinationDTO1;

    @Mock
    private DestinationDTO destinationDTO2;

    @Mock
    private Flight flight;

    @Mock
    private FlightDTO flightDTO;

    @Mock
    private Destination destination1;

    @Mock
    private Destination destination2;

    @Mock
    private DestinationService destinationService;

    @Autowired
    @InjectMocks
    private DestinationFacadeImpl destinationFacade;

    @Test
    public void createDestinationTest() {
        destinationFacade.createDestination("USA", "Boston");
        verify(destinationService).createDestination("USA", "Boston");
    }

    @Test
    public void removeDestinationTest() {
        when(destinationService.getDestinationById(flight.getId())).thenReturn(destination1);
        destinationFacade.removeDestination(destination1.getId());

        verify(destinationService).removeDestination(destination1);
    }

    @Test
    public void updateDestinationTest() {
        when(mappingService.mapTo(destinationDTO1, Destination.class)).thenReturn(destination1);
        destinationFacade.updateDestination(destinationDTO1);
        verify(destinationService).updateDestination(destination1);
    }

    @Test
    public void getDestinationByIdTest() {
        when(destinationService.getDestinationById(destination1.getId()))
                .thenReturn(destination1);
        when(mappingService.mapTo(destination1, DestinationDTO.class)).thenReturn(destinationDTO1);
        DestinationDTO destinationById = destinationFacade.getDestinationById(destination1.getId());

        verify(destinationService).getDestinationById(destination1.getId());
        Assert.assertTrue(destinationById.equals(destinationDTO1));
    }

    @Test
    public void getDestinationsByCountryTest() {
        List<Destination> destinations = new ArrayList<>();
        List<DestinationDTO> destinationDTOS = new ArrayList<>();
        destinations.add(destination1);
        destinations.add(destination2);
        destinationDTOS.add(destinationDTO1);
        destinationDTOS.add(destinationDTO2);

        when(destinationService.getDestinationsByCountry(any())).thenReturn(destinations);
        when(mappingService.mapTo(destinations, DestinationDTO.class)).thenReturn(destinationDTOS);

        List<DestinationDTO> destinationsByCountry = destinationFacade.getDestinationsByCountry(any());

        Assert.assertEquals(destinationsByCountry.size(), 2);
        Assert.assertTrue(destinationsByCountry.containsAll(Arrays.asList(destinationDTO1, destinationDTO2)));
    }

    @Test
    public void getDestinationsByCityTest() {
        when(destinationService.getDestinationsByCity("Boston")).thenReturn(Collections.singletonList(destination1));
        when(mappingService.mapTo(Collections.singletonList(destination1), DestinationDTO.class))
                .thenReturn(Collections.singletonList(destinationDTO1));

        List<DestinationDTO> destinationsByCountry = destinationFacade.getDestinationsByCity("Boston");

        Assert.assertEquals(destinationsByCountry.size(), 1);
        Assert.assertTrue(destinationsByCountry.contains(destinationDTO1));
    }

    @Test
    public void getAllDestinations() {
        List<DestinationDTO> destinationDTOS = Arrays.asList(destinationDTO1, destinationDTO2);
        List<Destination> destinations = Arrays.asList(destination1, destination2);

        when(destinationService.getAllDestinations())
                .thenReturn(destinations);
        when(mappingService.mapTo(Arrays.asList(destination1, destination2), DestinationDTO.class))
                .thenReturn(destinationDTOS);

        List<DestinationDTO> allDestinations = destinationFacade.getAllDestinations();

        Assert.assertEquals(allDestinations.size(), 2);
        Assert.assertTrue(allDestinations.containsAll(destinationDTOS));
    }

    @Test
    public void getAllIncomingFlightsTest() {
        List<Flight> flights = Collections.singletonList(flight);

        when(destinationService.getAllIncomingFlights(destination1))
                .thenReturn(flights);
        when(mappingService.mapTo(destinationDTO1, Destination.class))
                .thenReturn(destination1);
        when(mappingService.mapTo(flights, FlightDTO.class))
                .thenReturn(Collections.singletonList(flightDTO));

        List<FlightDTO> allIncomingFlights = destinationFacade.getAllIncomingFlights(destinationDTO1);

        Assert.assertEquals(allIncomingFlights.size(), 1);
        Assert.assertTrue(allIncomingFlights.contains(flightDTO));
    }

    @Test
    public void getAllOutgoingFlightsTest() {
        List<Flight> flights = Collections.singletonList(flight);

        when(destinationService.getAllOutgoingFlights(destination1))
                .thenReturn(flights);
        when(mappingService.mapTo(destinationDTO1, Destination.class))
                .thenReturn(destination1);
        when(mappingService.mapTo(flights, FlightDTO.class))
                .thenReturn(Collections.singletonList(flightDTO));

        List<FlightDTO> allIncomingFlights = destinationFacade.getAllOutgoingFlights(destinationDTO1);

        Assert.assertEquals(allIncomingFlights.size(), 1);
        Assert.assertTrue(allIncomingFlights.contains(flightDTO));
    }
}
