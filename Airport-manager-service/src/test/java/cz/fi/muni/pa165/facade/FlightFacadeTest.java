package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightCreateDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.dto.FlightUpdateDTO;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.service.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for FlightFacade
 *
 * @author Ondrej Prikryl
 */
public class FlightFacadeTest extends BaseFacadeTest {

    @Mock
    private FlightService flightService;

    @Mock
    private AirplaneService airplaneService;

    @Mock
    private StewardService stewardService;

    @Mock
    private MappingService mappingService;
    @Mock

    private DestinationService destinationService;

    @Mock
    private Flight flight;

    @Mock
    private Airplane airplane;

    @Mock
    private Steward steward;


    @Mock
    private FlightDTO flightDTO;

    @Mock
    private FlightCreateDTO flightCreateDTO;

    @Mock
    private FlightUpdateDTO flightUpdateDTO;

    @Autowired
    @InjectMocks
    private FlightFacadeImpl flightFacade;

    @BeforeMethod
    public void resetMocks() {
        Mockito.reset(mappingService, flightService);
    }

    @Test
    public void testCreateFlight() {
        when(flightCreateDTO.getAirplaneId()).thenReturn(1L);
        when(flightCreateDTO.getArrivalLocationId()).thenReturn(1L);
        when(flightCreateDTO.getArrivalTime()).thenReturn(LocalDateTime.now());
        when(flightCreateDTO.getDepartureTime()).thenReturn(LocalDateTime.now());
        when(flightCreateDTO.getDepartureLocationId()).thenReturn(1L);
        when(destinationService.getDestinationById(1L)).thenReturn(new Destination());
        when(airplaneService.findById(1L)).thenReturn(new Airplane());
        when(stewardService.getSteward(1L)).thenReturn(new Steward());

        flightFacade.createFlight(flightCreateDTO);
        verify(flightService).addFlight(any(Flight.class));
    }

    @Test
    public void testUpdateFlight() throws Exception {
        when(flightUpdateDTO.getAirplaneId()).thenReturn(1L);
        when(flightUpdateDTO.getArrivalLocationId()).thenReturn(1L);
        when(flightUpdateDTO.getArrivalTime()).thenReturn(LocalDateTime.now());
        when(flightUpdateDTO.getDepartureTime()).thenReturn(LocalDateTime.now());
        when(flightUpdateDTO.getDepartureLocationId()).thenReturn(1L);
        when(destinationService.getDestinationById(1L)).thenReturn(new Destination());
        when(airplaneService.findById(1L)).thenReturn(new Airplane());
        when(stewardService.getSteward(1L)).thenReturn(new Steward());

        flightFacade.updateFlight(flightUpdateDTO);
        verify(flightService).updateFlight(any(Flight.class));
    }

    @Test
    public void testGetFlight() {
        when(flightService.getFlight(flight.getId())).thenReturn(flight);
        when(mappingService.mapTo(flight, FlightDTO.class)).thenReturn(flightDTO);
        FlightDTO temp = flightFacade.getFlight(flight.getId());

        verify(flightService).getFlight(flight.getId());
        Assert.assertTrue(flightDTO.equals(temp));
    }

    @Test
    public void testGetFlightsSince() {
        List<Flight> flights = new ArrayList<>();
        List<FlightDTO> flightsDto = new ArrayList<>();
        flights.add(flight);
        flightsDto.add(flightDTO);

        when(flightService.getFlightsSince(any())).thenReturn(flights);
        when(mappingService.mapTo(flights, FlightDTO.class)).thenReturn(flightsDto);

        List<FlightDTO> temp = flightFacade.getFlightsSince(LocalDateTime.now());

        verify(flightService).getFlightsSince(any());
        for(int i = 0; i < flightsDto.size(); i++) {
            Assert.assertTrue(flightsDto.get(i).equals(temp.get(i)));
        }
    }

    @Test
    public void testGetCurrentFlights() {
        List<Flight> flights = new ArrayList<>();
        List<FlightDTO> flightsDto = new ArrayList<>();
        flights.add(flight);
        flightsDto.add(flightDTO);

        when(flightService.getCurrentFlights(any(LocalDateTime.class))).thenReturn(flights);
        when(mappingService.mapTo(flights, FlightDTO.class)).thenReturn(flightsDto);

        List<FlightDTO> temp = flightFacade.getCurrentFlights(LocalDateTime.now());

        Assert.assertEquals(flightsDto, temp);
        verify(flightService).getCurrentFlights(any(LocalDateTime.class));
    }

    @Test
    public void testGetFlightTime() {
        Duration dur = Duration.ofDays(1);

        when(flightService.getFlightTime(flight)).thenReturn(dur);
        when(mappingService.mapTo(flightDTO, Flight.class)).thenReturn(flight);

        Duration temp = flightFacade.getFlightTime(flightDTO);

        verify(flightService).getFlightTime(flight);
        Assert.assertTrue(dur.equals(temp));
    }

    @Test
    public void testGetAllFlights() {
        List<Flight> flights = new ArrayList<>();
        List<FlightDTO> flightsDto = new ArrayList<>();
        flights.add(flight);
        flightsDto.add(flightDTO);

        when(flightService.getAllFlights()).thenReturn(flights);
        when(mappingService.mapTo(flights, FlightDTO.class)).thenReturn(flightsDto);

        List<FlightDTO> temp = flightFacade.getAllFlights();

        verify(flightService).getAllFlights();
        for(int i = 0; i < flightsDto.size(); i++) {
            Assert.assertTrue(flightsDto.get(i).equals(temp.get(i)));
        }
    }

    @Test
    public void testDeleteFlight() {
        when(flightService.getFlight(flight.getId())).thenReturn(flight);
        flightFacade.deleteFlight(flight.getId());

        verify(flightService).deleteFlight(flight);
    }

    @Test
    public void testChangeArrivalTime() {
        when(flight.getDepartureTime()).thenReturn(LocalDateTime.of(2000, 2, 1, 6, 30));

        when(flightService.getFlight(flight.getId())).thenReturn(flight);
        flightFacade.changeArrivalTime(flight.getId(), LocalDateTime.of(2000, 2, 2, 6, 30));
    }

    @Test
    public void testAddStewardToFlight() {
        when(flightService.getFlight(flight.getId())).thenReturn(flight);
        when(stewardService.getSteward(steward.getId())).thenReturn(steward);

        flightFacade.addStewardToFlight(flight.getId(), steward.getId());
        verify(flightService).getFlight(flight.getId());
        verify(stewardService).getSteward(steward.getId());
        verify(flightService).addSteward(flight, steward);
    }

    @Test
    public void testChangeAirplane() {
        when(flightService.getFlight(flight.getId())).thenReturn(flight);
        when(airplaneService.findById(airplane.getId())).thenReturn(airplane);

        flightFacade.changeAirplane(flight.getId(), airplane.getId());

        verify(flightService).getFlight(flight.getId());
        verify(airplaneService).findById(airplane.getId());
        verify(flightService).updateFlight(flight);
    }
}