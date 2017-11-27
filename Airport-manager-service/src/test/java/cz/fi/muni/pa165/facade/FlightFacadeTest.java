package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.ServiceConfig;
import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.service.AirplaneService;
import cz.fi.muni.pa165.service.FlightService;
import cz.fi.muni.pa165.service.MappingService;
import cz.fi.muni.pa165.service.StewardService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Tests for FlightFacade
 *
 * @author Ondrej Prikryl
 * @date 27.11.2017
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class FlightFacadeTest {

    @Mock
    private FlightService flightService;

    @Mock
    private AirplaneService airplaneService;

    @Mock
    private StewardService stewardService;

    @Mock
    private MappingService mappingService;

    @Mock
    private Flight flight;

    @Mock
    private Airplane airplane;

    @Mock
    private Steward steward;


    @Mock
    FlightDTO flightDTO;

    @Mock
    FlightCreateDTO flightCreateDTO;

    @Autowired
    @InjectMocks
    private FlightFacadeImpl flightFacade;

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void resetMocks() {
        Mockito.reset(mappingService);
        Mockito.reset(flightService);
    }

    @Test
    public void testCreateFlight() {
        when(mappingService.mapTo(flightCreateDTO, Flight.class)).thenReturn(flight);
        flightFacade.createFlight(flightCreateDTO);
        verify(flightService).addFlight(flight);
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

    /*
    @Test
    public void testChangeArrivalTime() {
        flight.setDepartureTime(LocalDateTime.of(2000, 2, 1, 6, 30));

        when(flightService.getFlight(flight.getId())).thenReturn(flight);
        flightFacade.changeArrivalTime(flight.getId(), LocalDateTime.of(2000, 2, 2, 6, 30));

        verify(flightService).updateFlight(flight);
    }

    @Test
    public void testAddStewardToFlight() {
        when(flightService.getFlight(flight.getId())).thenReturn(flight);
        when(stewardService.getSteward(steward.getId())).thenReturn(steward);

        flightFacade.addStewardToFlight(flight.getId(), steward.getId());
        verify(flightService).getFlight(flight.getId());
        verify(stewardService).getSteward(steward.getId());
        verify(flightService).updateFlight(flight);
    }

    @Test
    public void testChangeAirplane() {
        when(flightService.getFlight(flight.getId())).thenReturn(flight);
        when(airplaneService.findById(airplane.getId())).thenReturn(airplane);

        flightFacade.changeAirplane(flight.getId(), airplane.getId());

        verify(flightService).getFlight(flight.getId());
        verify(airplaneService).findById(airplane.getId());
        verify(flightService).updateFlight(flight);
    }*/
}