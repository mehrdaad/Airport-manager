package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.service.impl.FlightServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 *
 * @author Ondrej Prikryl
 */
public class FlightServiceTest extends BaseServiceTest{
    private Flight flight;
    private Flight flight2;
    List<Steward> stewards;

    @Mock
    private FlightDao flightDao;

    @Autowired
    @InjectMocks
    private FlightServiceImpl flightService;

    @BeforeMethod
    public void setUpFlight() {
        createFlight();
    }

    @BeforeMethod
    public void resetMock() {
        Mockito.reset(flightDao);
    }

    @Test
    public void addFlightHappyDayScenario() {
        flight.setId(null);
        flightService.addFlight(flight);
        verify(flightDao).addFlight(flight);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void addFlightNull() {
        doThrow(new IllegalArgumentException()).when(flightDao).addFlight(null);
        flightService.addFlight(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void addFlightIdAlreadySet() {
        doThrow(new IllegalArgumentException()).when(flightDao).addFlight(flight);
        flightService.addFlight(flight);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void addFlightAfterDateBeforeDate() {
        LocalDateTime before = LocalDateTime.of(2000, 4,1,12,0);
        LocalDateTime after = LocalDateTime.of(2000, 5,1,12,0);
        flight.setDepartureTime(after);
        flight.setArrivalTime(before);
        flight.setId(null);

        doThrow(new IllegalArgumentException()).when(flightDao).addFlight(flight);
        flightService.addFlight(flight);
    }

    @Test
    public void deleteFlightHappyDayScenario() {
        flightService.deleteFlight(flight);
        verify(flightDao).deleteFlight(flight);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteFlightNull() {
        doThrow(new NullPointerException()).when(flightDao).deleteFlight(null);
        flightService.deleteFlight(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteFlightNullId() {
        flight.setId(null);

        doThrow(new DataIntegrityViolationException("Error while delete data in database"))
                .when(flightDao).deleteFlight(flight);
        flightService.deleteFlight(flight);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteFlightNonExisting() {
        doThrow(new DataIntegrityViolationException("Error while delete data in database"))
                .when(flightDao).deleteFlight(flight);
        flightService.deleteFlight(flight);
    }

    @Test
    public void updateFlightHappyDayScenario() {
        Airplane airplane2 = new Airplane();
        airplane2.setType("Rocket");
        airplane2.setName("Apollo");
        airplane2.setCapacity(5);

        flight.setAirPlane(airplane2);

        flightService.updateFlight(flight);
        verify(flightDao).updateFlight(flight);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateFlightNull() {
        doThrow(new NullPointerException()).when(flightDao).updateFlight(null);
        flightService.updateFlight(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateFlightNullId() {
        flight.setId(null);
        doThrow(new NullPointerException()).when(flightDao).updateFlight(flight);
        flightService.updateFlight(flight);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateFlightNotInDb() {
        doThrow(new DataIntegrityViolationException("Error while updating data in database"))
                .when(flightDao).updateFlight(flight);
        flightService.updateFlight(flight);
    }

    @Test
    public void getFlightHappyDayScenario() {
        when(flightDao.getFlight(flight.getId())).thenReturn(flight);
        Flight temp = flightService.getFlight(flight.getId());
        verify(flightDao).getFlight(flight.getId());

        Assert.assertTrue(flight.equals(temp));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void getFlightNull() {
        doThrow(new NullPointerException()).when(flightDao).getFlight(null);
        flightService.getFlight(null);
    }

    @Test
    public void getFlightNonExisting() {
        when(flightDao.getFlight(flight.getId())).thenReturn(null);
        Flight temp = flightService.getFlight(flight.getId());

        Assert.assertTrue(temp == null);
    }

    @Test
    public void getAllFlightsHappyScenario() {
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        flights.add(flight2);

        when(flightDao.getAllFlights()).thenReturn(flights);
        List<Flight> temp = flightService.getAllFlights();
        verify(flightDao).getAllFlights();

        for(int i = 0; i < flights.size(); i++) {
            Assert.assertTrue(temp.get(i).equals(flights.get(i)));
        }
    }

    @Test
    public void getFlightTimeHappyDayScenario() {
        LocalDateTime time1 = LocalDateTime.of(2000,5,1,6,30);
        LocalDateTime time2 = LocalDateTime.of(2000,5,1,8,0);
        Duration result = Duration.between(time1, time2);

        flight.setDepartureTime(time1);
        flight.setArrivalTime(time2);

        when(flightDao.getFlight(flight.getId())).thenReturn(flight);
        Duration temp = flightService.getFlightTime(flight);
        verify(flightDao).getFlight(flight.getId());
        Assert.assertTrue(result.equals(temp));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getFlightTimeNull() {
        flightService.getFlightTime(null);
        verifyZeroInteractions(flightDao.getFlight(any()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getFlightTimeNotInDb() {
        when(flightDao.getFlight(flight.getId())).thenReturn(null);
        flightService.getFlightTime(flight);
        verifyZeroInteractions(flightDao.getFlight(any()));
    }

    @Test
    public void getFlightsSinceHappyDayScenario() {
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        flights.add(flight2);

        when(flightDao.getAllFlights()).thenReturn(flights);
        List<Flight> result = flightService.getFlightsSince(LocalDateTime.of(2000, 3, 1, 10, 30));
        verify(flightDao).getAllFlights();
        Assert.assertTrue(result.size() == 2);
    }

    @Test
    public void getFlightsSinceHappyDayScenario2() {
        when(flightDao.getAllFlights()).thenReturn(new ArrayList<>());
        List<Flight> result = flightService.getFlightsSince(LocalDateTime.of(2000, 10, 1, 10, 30));
        verify(flightDao).getAllFlights();
        Assert.assertTrue(result.isEmpty());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getFlightsSinceNull() {
        flightService.getFlightsSince(null);
    }

    @Test
    public void getCurrentFlights() {
        LocalDateTime current = LocalDateTime.of(2000, 4, 1, 15, 30);
        when(flightDao.getAllFlights()).thenReturn(Arrays.asList(flight, flight2));

        List<Flight> result = flightService.getCurrentFlights(current);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(Collections.singletonList(flight), result);
        verify(flightDao).getAllFlights();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getCurrentFlightsNull() {
        List<Flight> result = flightService.getCurrentFlights(null);
    }

    @Test
    public void addStewardHappyDayScenario() {
        Steward steward = new Steward();
        steward.setId(1L);
        steward.setSurname("Novotny");
        steward.setFirstName("Petr");

        flightService.addSteward(flight, steward);
        verify(flightDao).updateFlight(any());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void addStewardNullSteward() {
        flightService.addSteward(flight, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void addStewardNullFlight() {
        Steward steward = new Steward();
        steward.setSurname("Novotny");
        steward.setFirstName("Petr");

        flightService.addSteward(null, steward);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addStewardAlreadyInFlight() {
        Steward steward = new Steward();
        steward.setSurname("Novotny");
        steward.setFirstName("Petr");

        List<Steward> stewards = flight.getStewards();
        stewards.add(steward);
        flight.setStewards(stewards);

        flightService.addSteward(flight, steward);
    }

    private void createFlight() {

        LocalDateTime time1 = LocalDateTime.of(2000, 4, 1, 10, 30);
        LocalDateTime time2 = LocalDateTime.of(2000, 5, 1, 10, 30);
        LocalDateTime time3 = LocalDateTime.of(2000, 6, 1, 10, 30);

        Airplane airplane1 = new Airplane();
        airplane1.setType("Jet");
        airplane1.setName("A-10");
        airplane1.setCapacity(15);

        Destination destination1 = new Destination();
        destination1.setCity("Brno");
        destination1.setCountry("CZ");

        Destination destination2 = new Destination();
        destination2.setCity("London");
        destination2.setCountry("UK");

        Steward steward1 = new Steward();
        steward1.setFirstName("Robert");
        steward1.setSurname("Duriancik");

        Steward steward2 = new Steward();
        steward2.setFirstName("Honza");
        steward2.setSurname("Cakl");

        stewards = new ArrayList<>();
        stewards.add(steward1);
        stewards.add(steward2);

        flight = new Flight();
        flight.setId(1L);
        flight.setAirPlane(airplane1);
        flight.setDepartureLocation(destination1);
        flight.setArrivalLocation(destination2);
        flight.setDepartureTime(time1);
        flight.setArrivalTime(time2);
        flight.setStewards(stewards);

        flight2 = new Flight();
        flight2.setId(2L);
        flight2.setAirPlane(airplane1);
        flight2.setDepartureLocation(destination2);
        flight2.setArrivalLocation(destination1);
        flight2.setDepartureTime(time2);
        flight2.setArrivalTime(time3);
        flight2.setStewards(stewards);
    }
}
