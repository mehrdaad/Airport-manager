package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Flight;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Karel Jiranek
 */
public class AirplaneServiceTest extends BaseServiceTest {
    @Mock
    private AirplaneDao airplaneDao;

    @Mock
    private FlightService flightService;

    @Autowired
    @InjectMocks
    private AirplaneService airplaneService;

    private Airplane airplaneA;
    private static final int AIRPLANE_A_CAPACITY = 400;
    private static final long AIRPLANE_A_ID = 5L;
    private static final String AIRPLANE_A_TYPE = "Large";
    private static final String AIRPLANE_A_NAME = "Boeing 747";

    private Airplane airplaneB;
    private static final int AIRPLANE_B_CAPACITY = 10;
    private static final long AIRPLANE_B_ID = 4L;
    private static final String AIRPLANE_B_TYPE = "Small";
    private static final String AIRPLANE_B_NAME = "Cesna";

    private Flight flight;
    private static final LocalDateTime FLIGHT_DEPARTURE_TIME = LocalDateTime.of(2018,
            Month.DECEMBER,
            24,
            8,
            30);
    private static final LocalDateTime ONE_DAY_BEFORE_FLIGHT_DEPARTURE = LocalDateTime.of(2018,
            Month.DECEMBER,
            23,
            8,
            30);
    private static final LocalDateTime ONE_DAY_AFTER_FLIGHT_DEPARTURE = LocalDateTime.of(2018,
            Month.DECEMBER,
            25,
            8,
            30);


    @BeforeMethod
    public void prepareAirplaneAndFlight(){
        airplaneA = new Airplane();
        airplaneA.setId(AIRPLANE_A_ID);
        airplaneA.setType(AIRPLANE_A_TYPE);
        airplaneA.setName(AIRPLANE_A_NAME);
        airplaneA.setCapacity(AIRPLANE_A_CAPACITY);

        airplaneB = new Airplane();
        airplaneB.setId(AIRPLANE_B_ID);
        airplaneB.setType(AIRPLANE_B_TYPE);
        airplaneB.setName(AIRPLANE_B_NAME);
        airplaneB.setCapacity(AIRPLANE_B_CAPACITY);

        flight = new Flight();
        flight.setDepartureTime(FLIGHT_DEPARTURE_TIME);
        flight.setAirPlane(airplaneA);
    }


    @Test
    public void findAllTest(){
        when(airplaneDao.findAll()).thenReturn(Arrays.asList(airplaneA, airplaneB));
        List<Airplane> allFlights = airplaneService.findAll();

        Assert.assertEquals(allFlights.size(), 2);
        Assert.assertTrue(allFlights.get(0).getId().equals(AIRPLANE_A_ID) || allFlights.get(0).getId().equals(AIRPLANE_B_ID));
    }

    @Test
    public void findByNameTest(){
        when(airplaneDao.findByName(AIRPLANE_A_NAME)).thenReturn(Collections.singletonList(airplaneA));
        List<Airplane> allFlights = airplaneService.findByName(AIRPLANE_A_NAME);

        Assert.assertEquals(allFlights.size(), 1);
        Assert.assertTrue(allFlights.get(0).getId().equals(AIRPLANE_A_ID));
    }

    @Test
    public void findFindByTypeTest(){
        when(airplaneDao.findByType(AIRPLANE_B_TYPE)).thenReturn(Collections.singletonList(airplaneB));
        List<Airplane> allFlights = airplaneService.findByType(AIRPLANE_B_TYPE);

        Assert.assertEquals(allFlights.size(), 1);
        Assert.assertTrue(allFlights.get(0).getId().equals(AIRPLANE_B_ID));

        verify(airplaneDao).findByType(AIRPLANE_B_TYPE);
    }

    @Test
    public void findByCapacityMinTest(){
        when(airplaneDao.findByCapacityMin(AIRPLANE_B_CAPACITY)).thenReturn(Arrays.asList(airplaneA, airplaneB));
        when(airplaneDao.findByCapacityMin(AIRPLANE_A_CAPACITY)).thenReturn(Collections.singletonList(airplaneA));
        when(airplaneDao.findByCapacityMin(AIRPLANE_A_CAPACITY + 1)).thenReturn(null);

        List<Airplane> allFlights = airplaneService.findByCapacityMin(AIRPLANE_B_CAPACITY);
        verify(airplaneDao).findByCapacityMin(AIRPLANE_B_CAPACITY);
        Assert.assertEquals(allFlights.size(), 2);

        allFlights = airplaneService.findByCapacityMin(AIRPLANE_A_CAPACITY);
        verify(airplaneDao).findByCapacityMin(AIRPLANE_A_CAPACITY);
        Assert.assertEquals(allFlights.size(), 1);
        Assert.assertTrue(allFlights.get(0).getId().equals(AIRPLANE_A_ID));

        allFlights = airplaneService.findByCapacityMin(AIRPLANE_A_CAPACITY + 1);
        verify(airplaneDao).findByCapacityMin(AIRPLANE_A_CAPACITY + 1);
        Assert.assertNull(allFlights);
    }

    @Test
    public void findByCapacityMaxTest(){
        when(airplaneDao.findByCapacityMax(AIRPLANE_A_CAPACITY)).thenReturn(Arrays.asList(airplaneA, airplaneB));
        when(airplaneDao.findByCapacityMax(AIRPLANE_B_CAPACITY)).thenReturn(Collections.singletonList(airplaneB));
        when(airplaneDao.findByCapacityMax(AIRPLANE_B_CAPACITY - 1)).thenReturn(null);

        List<Airplane> allFlights = airplaneService.findByCapacityMax(AIRPLANE_A_CAPACITY);
        verify(airplaneDao).findByCapacityMax(AIRPLANE_A_CAPACITY);
        Assert.assertEquals(allFlights.size(), 2);

        allFlights = airplaneService.findByCapacityMax(AIRPLANE_B_CAPACITY);
        verify(airplaneDao).findByCapacityMax(AIRPLANE_B_CAPACITY);
        Assert.assertEquals(allFlights.size(), 1);
        Assert.assertTrue(allFlights.get(0).getId().equals(AIRPLANE_B_ID));

        allFlights = airplaneService.findByCapacityMax(AIRPLANE_B_CAPACITY - 1);
        verify(airplaneDao).findByCapacityMax(AIRPLANE_B_CAPACITY - 1);
        Assert.assertNull(allFlights);
    }

    @Test
    public void findByUsedAfterDateTimeTest(){
        // TODO WTF the <airplane>.findByUsedAfterDateTimeTest is doing?
        // TODO Well I can test implemented behavior but logic is not clear to me.
        when(flightService.getFlightsSince(ONE_DAY_BEFORE_FLIGHT_DEPARTURE))
        .thenReturn(Collections.singletonList(flight));

        when(flightService.getFlightsSince(ONE_DAY_AFTER_FLIGHT_DEPARTURE))
                .thenReturn(null);

        when(airplaneDao.findAll()).thenReturn(Arrays.asList(airplaneA, airplaneB));

        List<Airplane> airplanesUsedAfterDate = airplaneService.findByUsedAfterDateTime(ONE_DAY_BEFORE_FLIGHT_DEPARTURE);
        Assert.assertEquals(airplanesUsedAfterDate.size(), 1);
        Assert.assertTrue(airplanesUsedAfterDate.get(0).getId().equals(AIRPLANE_A_ID));

        airplanesUsedAfterDate = airplaneService.findByUsedAfterDateTime(ONE_DAY_AFTER_FLIGHT_DEPARTURE);
        Assert.assertTrue(airplanesUsedAfterDate.isEmpty());
    }



    @Test
    public void findByFreeAfterDateTime(){
        // TODO I am not sure what to test
    }


}
