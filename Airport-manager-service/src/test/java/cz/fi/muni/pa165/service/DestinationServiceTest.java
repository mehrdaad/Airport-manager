package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.DestinationDao;
import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.exceptions.DestinationDataAccessException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Karel Jiranek
 */
public class DestinationServiceTest extends BaseServiceTest  {
    @Mock
    private DestinationDao destinationDao;

    @Mock
    private FlightDao flightDao;

    @Autowired
    @InjectMocks
    private  DestinationService destinationService;

    private final String  DESTINATION_AB_COUNTRY = "USA";

    private final Destination destinationA = new Destination();
    private final String  DESTINATION_A_CITY = "Boston";
    private final long  DESTINATION_A_ID = 4L;

    private final Destination destinationB = new Destination();
    private final String  DESTINATION_B_CITY = "NYC";
    private final long  DESTINATION_B_ID = 6L;

    private final long  UNSIGNED_ID = 5L;
    private final Destination nullDestination = null;

    private final Flight flightA = new Flight();
    private final long  FLIGHT_A_ID = 1L;
    private final Flight flightB = new Flight();
    private final long  FLIGHT_B_ID = 2L;
    private final Flight flightC = new Flight();
    private final long  FLIGHT_C_ID = 3L;

    @BeforeMethod
    public void prepare(){
        destinationA.setCountry(DESTINATION_AB_COUNTRY);
        destinationA.setCity(DESTINATION_A_CITY);
        destinationA.setId(DESTINATION_A_ID);

        destinationB.setCountry(DESTINATION_AB_COUNTRY);
        destinationB.setCity(DESTINATION_B_CITY);
        destinationB.setId(DESTINATION_B_ID);

        flightA.setId(FLIGHT_A_ID);
        flightB.setId(FLIGHT_B_ID);
        flightC.setId(FLIGHT_C_ID);

        flightA.setArrivalLocation(destinationA);
        flightB.setArrivalLocation(destinationA);
        flightC.setArrivalLocation(destinationB);

        flightA.setDepartureLocation(destinationB);
        flightB.setDepartureLocation(destinationB);
        flightC.setDepartureLocation(destinationA);
    }

    @Test (expectedExceptions = DestinationDataAccessException.class)
    public void removeDestinationNullTest(){
        doThrow(new NullPointerException())
                .when(destinationDao).removeDestination(any(Destination.class));
        destinationService.removeDestination(null);
    }

    @Test (expectedExceptions = DestinationDataAccessException.class)
    public void createDestinationCountryNullTest(){
        doThrow(new NullPointerException())
                .when(destinationDao).addDestination(any(Destination.class));
        destinationService.createDestination(null, "Boston");
    }

    @Test (expectedExceptions = DestinationDataAccessException.class)
    public void createDestinationCityNullTest(){
        doThrow(new NullPointerException())
                .when(destinationDao).addDestination(any(Destination.class));
        destinationService.createDestination("USA", null);
    }

    @Test (expectedExceptions = DestinationDataAccessException.class)
    public void updateDestinationNullTest(){
        doThrow(new NullPointerException())
                .when(destinationDao).updateDestination(any(Destination.class));
        destinationService.updateDestination(null);
    }

    @Test
    public void getDestinationsByIdTest(){
        when(destinationDao.getDestination(DESTINATION_A_ID))
                .thenReturn(destinationA);
        Destination destinationA = destinationService.getDestinationById(DESTINATION_A_ID);

        Assert.assertTrue(destinationA.getId().equals(DESTINATION_A_ID));
        verify(destinationDao).getDestination(DESTINATION_A_ID);
    }

    @Test
    public void getDestinationsByNonExistingIdTest(){
        when(destinationDao.getDestination(UNSIGNED_ID))
                .thenReturn(null);
        Destination destinationA = destinationService.getDestinationById(UNSIGNED_ID);

        Assert.assertNull(destinationA);
        verify(destinationDao).getDestination(UNSIGNED_ID);
    }

    @Test
    public void getDestinationsByCityTest(){
        when(destinationDao.getDestinationsByCity(DESTINATION_A_CITY))
                .thenReturn(Arrays.asList(destinationA));
        List<Destination> destinationsByCity = destinationService.getDestinationsByCity(DESTINATION_A_CITY);

        Assert.assertNotNull(destinationsByCity);
        Assert.assertTrue(destinationsByCity.get(0).getId().equals(DESTINATION_A_ID));
        verify(destinationDao).getDestinationsByCity(DESTINATION_A_CITY);
    }

    @Test
    public void getDestinationsByNonExistingCityTest(){
        when(destinationDao.getDestinationsByCity("AAA"))
                .thenReturn(null);
        List<Destination> destinationsByCity = destinationService.getDestinationsByCity("AAA");

        Assert.assertNull(destinationsByCity);
        verify(destinationDao).getDestinationsByCity("AAA");
    }

    @Test
    public void getDestinationsByCountryTest(){
        when(destinationDao.getDestinationsByCountry(DESTINATION_AB_COUNTRY))
                .thenReturn(Arrays.asList(destinationA, destinationB));
        List<Destination> destinationsByCountry = destinationService.getDestinationsByCountry(DESTINATION_AB_COUNTRY);

        Assert.assertNotNull(destinationsByCountry);
        Assert.assertEquals(destinationsByCountry.size(), 2);
        verify(destinationDao).getDestinationsByCountry(DESTINATION_AB_COUNTRY);
    }

    @Test
    public void getDestinationsByNonExistingCountryTest(){
        when(destinationDao.getDestinationsByCity("NOPE GO LAND"))
                .thenReturn(null);
        List<Destination> destinationsByCountry = destinationService.getDestinationsByCountry("NOPE GO LAND");

        Assert.assertNotNull(destinationsByCountry);
        Assert.assertEquals(destinationsByCountry.size(), 0);
        verify(destinationDao).getDestinationsByCountry("NOPE GO LAND");
    }

    @Test
    public void getAllDestinationsTest(){
        when(destinationDao.getAllDestinations())
                .thenReturn(Arrays.asList(destinationA, destinationB));
        List<Destination> allDestinations = destinationService.getAllDestinations();

        Assert.assertNotNull(allDestinations);
        Assert.assertEquals(allDestinations.size(), 2);
    }

    @Test
    public void getAllDestinationsEmptyTest(){
        when(destinationDao.getAllDestinations())
                .thenReturn(Collections.emptyList());
        List<Destination> allDestinations = destinationService.getAllDestinations();

        Assert.assertNotNull(allDestinations);
        Assert.assertEquals(allDestinations.size(), 0);
    }

    @Test
    public void getAllIncomingFlightsTest(){
        when(flightDao.getAllFlights())
                .thenReturn(Arrays.asList(flightA, flightB, flightC));
        List<Flight> allIncomingFlights = destinationService.getAllIncomingFlights(destinationA);

        Assert.assertNotNull(allIncomingFlights);
        Assert.assertEquals(allIncomingFlights.size(), 2);

        allIncomingFlights.containsAll(Arrays.asList(flightA, flightB));
    }

    @Test
    public void getAllIncomingFlightsNoFlightsTest(){
        when(flightDao.getAllFlights())
                .thenReturn(Collections.emptyList());
        List<Flight> allIncomingFlights = destinationService.getAllIncomingFlights(destinationA);

        Assert.assertNotNull(allIncomingFlights);
        Assert.assertEquals(allIncomingFlights.size(), 0);
    }

    @Test
    public void getAllOutgoingFlightsTest(){
        when(flightDao.getAllFlights())
                .thenReturn(Arrays.asList(flightA, flightB, flightC));
        List<Flight> allOutgoingFlights = destinationService.getAllOutgoingFlights(destinationB);

        Assert.assertNotNull(allOutgoingFlights);
        Assert.assertEquals(allOutgoingFlights.size(), 2);

        Assert.assertTrue(allOutgoingFlights.containsAll(Arrays.asList(flightA, flightB)));
    }

    @Test
    public void getAllOutgoingFlightsNoFlightsTest(){
        when(flightDao.getAllFlights())
                .thenReturn(Collections.emptyList());
        List<Flight> allOutgoingFlights = destinationService.getAllOutgoingFlights(destinationA);

        Assert.assertNotNull(allOutgoingFlights);
        Assert.assertEquals(allOutgoingFlights.size(), 0);
    }
}
