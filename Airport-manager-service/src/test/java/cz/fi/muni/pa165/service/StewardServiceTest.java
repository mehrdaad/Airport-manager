package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.StewardDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.service.impl.StewardServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 *
 * @author Jan Cakl
 */
public class StewardServiceTest extends BaseServiceTest{
    
    private Steward steward;
    private Steward steward1;
    
    private Airplane airplane;

    
    private  LocalDateTime departureTime_future;
    private LocalDateTime arrivalTime_future;
    private  LocalDateTime departureTime_future1;
    private LocalDateTime arrivalTime_future1;
    private  LocalDateTime departureTime_past;
    private LocalDateTime arrivalTime_past;

    
    @Mock
    private StewardDao stewardDao;
    
    @Mock
    private FlightService flightService;

    @Autowired
    @InjectMocks
    private StewardServiceImpl stewardService;
    
    @BeforeMethod
    public void setUpSteward() {
        createSteward();
    }
    
    @BeforeMethod
    public void setUpAirplane() {
        createAirplane();
    }
    
    @BeforeMethod
    public void resetMock() {
        Mockito.reset(stewardDao);
        Mockito.reset(flightService);
    }
    
    private void createSteward() {
        
        steward = new Steward();
        steward.setId(1L);
        steward.setFirstName("Name1");
        steward.setSurname("Surname1");
        
        steward1 = new Steward();
        steward1.setId(2L);
        steward1.setFirstName("Name2");
        steward1.setSurname("Surname2"); 
    }

    private void createAirplane() {
        
        departureTime_future = LocalDateTime.of(2020, Month.NOVEMBER, 20, 8, 30);
        arrivalTime_future = LocalDateTime.of(2030, Month.NOVEMBER, 25, 20, 30);
        
        departureTime_future1 = LocalDateTime.of(2025, Month.NOVEMBER, 20, 8, 30);
        arrivalTime_future1 = LocalDateTime.of(2035, Month.NOVEMBER, 25, 20, 30);
        
        departureTime_past = LocalDateTime.of(2000, Month.NOVEMBER, 20, 8, 30);
        arrivalTime_past = LocalDateTime.of(2000, Month.NOVEMBER, 25, 20, 30);

        airplane = new Airplane();
        airplane.setId(1L);
        airplane.setCapacity(10);
        airplane.setName("Airplane1");
        airplane.setType("AK-447");
    }
    
    @Test
    public void createStewardOkTest() {
        stewardService.createSteward("Name1", "Surname1");
        verify(stewardDao).createSteward(steward);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void createStewardIdAlreadySetTest() {
        doThrow(new IllegalArgumentException()).when(stewardDao).createSteward(steward);
        stewardService.createSteward("Name1", "Surname1");
    }
    
    @Test
    public void deleteStewardOkTest() {
        stewardService.deleteSteward(steward);
        verify(stewardDao).deleteSteward(steward);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void deleteFlightNullTest() {
        doThrow(new IllegalArgumentException()).when(stewardDao).deleteSteward(null);
        stewardService.deleteSteward(null);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void deleteStewardNullIdTest() {
        steward.setId(null);

        doThrow(new DataIntegrityViolationException("Error while delete data in database")).when(stewardDao).deleteSteward(steward);
        stewardService.deleteSteward(steward);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void deleteStewardNoExistingTest() {
        doThrow(new DataIntegrityViolationException("Error while delete data in database")).when(stewardDao).deleteSteward(steward);
        stewardService.deleteSteward(steward);
    }
    
    @Test
    public void updateStewardOkTest() {
        steward.setFirstName("Karel");

        stewardService.updateSteward(steward);
        verify(stewardDao).updateSteward(steward);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateStewardNullTest() {
        doThrow(new IllegalArgumentException()).when(stewardDao).updateSteward(null);
        stewardService.updateSteward(null);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void updateStewardNullIdTest() {
        steward.setId(null);
        doThrow(new NullPointerException()).when(stewardDao).updateSteward(steward);
        stewardService.updateSteward(steward);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void updateStewardNotInDbTest() {
        doThrow(new DataIntegrityViolationException("Error while updating data in database")).when(stewardDao).updateSteward(steward);
        stewardService.updateSteward(steward);
    }
    
    @Test
    public void getStewardOkTest() {
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        Steward tmp = stewardService.getSteward(steward.getId());
        verify(stewardDao).getSteward(steward.getId());

        Assert.assertTrue(steward.equals(tmp));
    }
    
    @Test
    public void getStewardNoExistingTest() {
        when(flightService.getFlight(steward.getId())).thenReturn(null);
        Steward tmp = stewardService.getSteward(steward.getId());

        Assert.assertTrue(tmp == null);
    }
    
    @Test
    public void getAllStewardsNameOrderedOkTest() {
        List<Steward> stewards = new ArrayList<>();
        stewards.add(steward);
        stewards.add(steward1);

        when(stewardDao.listAllStewards()).thenReturn(stewards);
        List<Steward> tmp = stewardService.getAllStewardsNameOrdered();
        verify(stewardDao).listAllStewards();

        for(int i = 0; i < stewards.size(); i++) {
            Assert.assertTrue(tmp.get(i).equals(stewards.get(i)));
        }
    }
    
    @Test
    public void getAllStewardFlightsTest() {
        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_past);
        flight1.setArrivalTime(arrivalTime_past);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward1);
        
        Flight flight2 = new Flight();
        flight2.setAirplane(airplane);
        flight2.setDepartureTime(departureTime_future);
        flight2.setArrivalTime(arrivalTime_future);
        flight2.setDepartureLocation(new Destination());
        flight2.addSteward(steward1);
        
        Flight flight3 = new Flight();
        flight3.setAirplane(airplane);
        flight3.setDepartureTime(departureTime_future1);
        flight3.setArrivalTime(arrivalTime_future1);
        flight3.setDepartureLocation(new Destination());
        flight3.addSteward(steward);
        
        List<Flight> flights_expected_steward1 = new ArrayList<>();
        flights_expected_steward1.add(flight2);
        flights_expected_steward1.add(flight1);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight3);
        flights.add(flight2);
        flights.add(flight1);

        when(stewardDao.getSteward(steward1.getId())).thenReturn(steward1);
        when(flightService.getAllFlights()).thenReturn(flights);
        
        assertEquals(stewardService.getAllStewardFlights(steward1.getId()),flights_expected_steward1);

        List<Flight> flights_expected_steward = new ArrayList<>();
        flights_expected_steward.add(flight3);
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(flights);
        
        assertEquals(stewardService.getAllStewardFlights(steward.getId()),flights_expected_steward);
        
        
        List<Flight> flights_without_steward = new ArrayList<>();
        flights_without_steward.add(flight2);
        flights_without_steward.add(flight1);
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(flights_without_steward);
        
        assertEquals(stewardService.getAllStewardFlights(steward.getId()),new ArrayList<>());
    }
    
    @Test
    public void getStewardCurrentFlightTest() {
     
        LocalDateTime departureTime_current = LocalDateTime.of(2017, Month.NOVEMBER, 20, 8, 30);
        LocalDateTime arrivalTime_current = LocalDateTime.of(2100, Month.NOVEMBER, 25, 20, 30);
        
        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_current);
        flight1.setArrivalTime(arrivalTime_current);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward1);
        
        Flight flight2 = new Flight();
        flight2.setAirplane(airplane);
        flight2.setDepartureTime(departureTime_past);
        flight2.setArrivalTime(arrivalTime_past);
        flight2.setDepartureLocation(new Destination());
        flight2.addSteward(steward);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight2);
        flights.add(flight1);
        
        when(stewardDao.getSteward(steward1.getId())).thenReturn(steward1);
        when(flightService.getAllFlights()).thenReturn(flights);

        assertEquals(stewardService.getStewardCurrentFlight(steward1.getId()),flight1);
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(flights);

        assertNull(stewardService.getStewardCurrentFlight(steward.getId()));
        
        List<Flight> flights_empty = new ArrayList<>();
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(flights_empty);
        
        assertNull(stewardService.getStewardCurrentFlight(steward.getId()));
    }
    
    @Test
    public void getStewardFutureFlightTest() {

        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_past);
        flight1.setArrivalTime(arrivalTime_past);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward);
        
        Flight flight2 = new Flight();
        flight2.setAirplane(airplane);
        flight2.setDepartureTime(departureTime_future);
        flight2.setArrivalTime(arrivalTime_future);
        flight2.setDepartureLocation(new Destination());
        flight2.addSteward(steward1);
        
        Flight flight3 = new Flight();
        flight3.setAirplane(airplane);
        flight3.setDepartureTime(departureTime_future);
        flight3.setArrivalTime(arrivalTime_future);
        flight3.setDepartureLocation(new Destination());
        flight3.addSteward(steward);
        
        Flight flight4 = new Flight();
        flight4.setAirplane(airplane);
        flight4.setDepartureTime(departureTime_future1);
        flight4.setArrivalTime(arrivalTime_future1);
        flight4.setDepartureLocation(new Destination());
        flight4.addSteward(steward1);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight4);
        flights.add(flight3);
        flights.add(flight2);
        flights.add(flight1);
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(flights);
        
        assertEquals(flight3,stewardService.getStewardFutureFlight(steward.getId()));
        
        List<Flight> flights_empty = new ArrayList<>();
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(flights_empty);
        
        assertNull(stewardService.getStewardCurrentFlight(steward.getId()));
        
        when(stewardDao.getSteward(steward1.getId())).thenReturn(steward1);
        when(flightService.getAllFlights()).thenReturn(flights);
        
        assertNull(stewardService.getStewardCurrentFlight(steward1.getId()));
    }
    @Test
    public void getAllStewardFlightsInTimeRangeTest(){
        
        Steward steward3 = new Steward();
        steward3.setId(3L);
        steward3.setFirstName("Name3");
        steward3.setSurname("Surname3");
        
        LocalDateTime startTime = LocalDateTime.of(2019, Month.NOVEMBER, 19, 8, 30);
        LocalDateTime stopTime = LocalDateTime.of(2026, Month.NOVEMBER, 26, 20, 30);
        
        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_past);
        flight1.setArrivalTime(arrivalTime_past);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward);
        
        Flight flight2 = new Flight();
        flight2.setAirplane(airplane);
        flight2.setDepartureTime(departureTime_future);
        flight2.setArrivalTime(arrivalTime_future);
        flight2.setDepartureLocation(new Destination());
        flight2.addSteward(steward);
        
        Flight flight3 = new Flight();
        flight3.setAirplane(airplane);
        flight3.setDepartureTime(departureTime_future1);
        flight3.setArrivalTime(arrivalTime_future1);
        flight3.setDepartureLocation(new Destination());
        flight3.addSteward(steward1);
        
        Flight flight4 = new Flight();
        flight4.setAirplane(airplane);
        flight4.setDepartureTime(departureTime_past);
        flight4.setArrivalTime(arrivalTime_past);
        flight4.setDepartureLocation(new Destination());
        flight4.addSteward(steward3);
        
        List<Flight> flight = new ArrayList<>();
        flight.add(flight2);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight4);
        flights.add(flight3);
        flights.add(flight2);
        flights.add(flight1);
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(flights);
        
        assertEquals(stewardService.getAllStewardFlightsInTimeRange(steward.getId(), startTime, stopTime), flight);
        
        List<Flight> flight_steward1 = new ArrayList<>();
        flight_steward1.add(flight3);
        
        when(stewardDao.getSteward(steward1.getId())).thenReturn(steward1);
        when(flightService.getAllFlights()).thenReturn(flights);
        
        assertEquals(stewardService.getAllStewardFlightsInTimeRange(steward1.getId(), startTime, stopTime), flight_steward1);
        
        when(stewardDao.getSteward(steward3.getId())).thenReturn(steward3);
        when(flightService.getAllFlights()).thenReturn(flights);
        
        assertEquals(stewardService.getAllStewardFlightsInTimeRange(steward3.getId(), startTime, stopTime), new ArrayList<>());
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(new ArrayList<>());
        
        assertEquals(stewardService.getAllStewardFlightsInTimeRange(steward.getId(), startTime, stopTime),new ArrayList<>());
    }
    
    @Test
    public void getStewardLastFlightTest(){
        
        Steward steward3 = new Steward();
        steward3.setId(3L);
        steward3.setFirstName("Name33");
        steward3.setSurname("Surname33");
        
        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_past);
        flight1.setArrivalTime(arrivalTime_past);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward);
        
        Flight flight2 = new Flight();
        flight2.setAirplane(airplane);
        flight2.setDepartureTime(departureTime_future1);
        flight2.setArrivalTime(arrivalTime_future1);
        flight2.setDepartureLocation(new Destination());
        flight2.addSteward(steward1);
        
        Flight flight3 = new Flight();
        flight3.setAirplane(airplane);
        flight3.setDepartureTime(departureTime_future);
        flight3.setArrivalTime(arrivalTime_future);
        flight3.setDepartureLocation(new Destination());
        flight3.addSteward(steward);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight3);
        flights.add(flight2);
        flights.add(flight1);
        
        when(stewardDao.getSteward(steward.getId())).thenReturn(steward);
        when(flightService.getAllFlights()).thenReturn(flights);
        
        assertEquals(stewardService.getStewardLastFlight(steward.getId()), flight3);
    }

    @Test
    public void getFreeStewardsInTimeRangeTest2() {
        Steward steward3 = new Steward();
        steward3.setId(3L);
        steward3.setFirstName("Name33");
        steward3.setSurname("Surname33");

        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_past);
        flight1.setArrivalTime(arrivalTime_past);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward);
        flight1.addSteward(steward1);

        Flight flight2 = new Flight();
        flight2.setAirplane(airplane);
        flight2.setDepartureTime(departureTime_future1);
        flight2.setArrivalTime(arrivalTime_future1);
        flight2.setDepartureLocation(new Destination());
        flight2.addSteward(steward1);
        flight2.addSteward(steward3);

        when(flightService.getFlightsInTimeRange(departureTime_past, arrivalTime_past)).thenReturn(Collections.singletonList(flight1));
        when(stewardDao.listAllStewards()).thenReturn(Arrays.asList(steward, steward1, steward3));

        List<Steward> result = stewardService.getFreeStewardsInTimeRange(departureTime_past, arrivalTime_past);

        assertEquals(result.size(), 1);
        assertTrue(result.contains(steward3));
    }

    @Test
    public void getFreeStewardsInTimeRangeTest() {
        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_past);
        flight1.setArrivalTime(arrivalTime_past);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward);

        Flight flight2 = new Flight();
        flight2.setAirplane(airplane);
        flight2.setDepartureTime(departureTime_future1);
        flight2.setArrivalTime(arrivalTime_future1);
        flight2.setDepartureLocation(new Destination());
        flight2.addSteward(steward1);

        Steward steward3 = new Steward();
        steward3.setId(3L);
        steward3.setFirstName("Name33");
        steward3.setSurname("Surname33");


        when(flightService.getFlightsInTimeRange(departureTime_past, arrivalTime_past)).thenReturn(Collections.singletonList(flight1));
        when(stewardDao.listAllStewards()).thenReturn(Arrays.asList(steward, steward1, steward3));

        List<Steward> result = stewardService.getFreeStewardsInTimeRange(departureTime_past, arrivalTime_past);

        assertEquals(result.size(), 2);
        assertTrue(result.contains(steward1));
        assertTrue(result.contains(steward3));
    }
}
