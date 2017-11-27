package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class AirplaneServiceTest extends BaseServiceTest {
    @Mock
    private AirplaneDao airplaneDao;
    @Mock
    private FlightService flightService;

    @Autowired
    @InjectMocks
    private AirplaneService airplaneService;

    private Airplane airplane;
    private Long airplaneId;
    private String airplaneName;
    private String airplaneType;
    private int airplaneCapacity;

    @BeforeMethod
    public void prepareTestData() {
        airplaneId = 1L;
        airplaneName = "Nunez";
        airplaneType = "Rachotina";
        airplaneCapacity = 100;

        airplane = new Airplane();
        airplane.setId(airplaneId);
        airplane.setName(airplaneName);
        airplane.setType(airplaneType);
        airplane.setCapacity(airplaneCapacity);

        Mockito.reset(airplaneDao, flightService);
    }

    @Test
    public void testFindById() throws Exception {
        when(airplaneDao.findById(airplaneId)).thenReturn(airplane);

        Airplane returnedAirplane = airplaneService.findById(airplaneId);

        Assert.assertEquals(returnedAirplane, airplane);
        verify(airplaneDao).findById(airplaneId);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testFindById_exceptionHandling() throws Exception {
        doThrow(new RuntimeException()).when(airplaneDao).findById(null);

        airplaneService.findById(null);
    }

    @Test
    public void testDeleteAirplane() throws Exception {
        airplaneService.deleteAirplane(airplane);

        verify(airplaneDao).deleteAirplane(airplane);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testDeleteAirplane_exceptionHandling() throws Exception {
        doThrow(new RuntimeException()).when(airplaneDao).deleteAirplane(null);

        airplaneService.deleteAirplane(null);
    }

    @Test
    public void testAddAirplane() throws Exception {
        airplaneService.addAirplane(airplane);

        verify(airplaneDao).addAirplane(airplane);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testAddAirplane_exceptionHandling() throws Exception {
        doThrow(new RuntimeException()).when(airplaneDao).addAirplane(null);

        airplaneService.addAirplane(null);
    }

    @Test
    public void testUpdateAirplane() throws Exception {
        airplaneService.updateAirplane(airplane);

        verify(airplaneDao).updateAirplane(airplane);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testUpdateAirplane_exceptionHandling() throws Exception {
        doThrow(new RuntimeException()).when(airplaneDao).updateAirplane(null);

        airplaneService.updateAirplane(null);
    }

    @Test
    public void testFindAll() throws Exception {
        when(airplaneDao.findAll()).thenReturn(Collections.singletonList(airplane));

        List<Airplane> returnedAirplanes = airplaneService.findAll();

        Assert.assertEquals(returnedAirplanes, Collections.singletonList(airplane));
        verify(airplaneDao).findAll();
    }

    @Test
    public void testFindByName() throws Exception {
        when(airplaneDao.findByName(airplaneName)).thenReturn(Collections.singletonList(airplane));

        List<Airplane> returnedAirplanes = airplaneService.findByName(airplaneName);

        Assert.assertEquals(returnedAirplanes, Collections.singletonList(airplane));
        verify(airplaneDao).findByName(airplaneName);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testFindByName_exceptionHandling() throws Exception {
        doThrow(new RuntimeException()).when(airplaneDao).findByName(null);

        airplaneService.findByName(null);
    }

    @Test
    public void testFindByType() throws Exception {
        when(airplaneDao.findByType(airplaneType)).thenReturn(Collections.singletonList(airplane));

        List<Airplane> returnedAirplanes = airplaneService.findByType(airplaneType);

        Assert.assertEquals(returnedAirplanes, Collections.singletonList(airplane));
        verify(airplaneDao).findByType(airplaneType);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testFindByType_exceptionHandling() throws Exception {
        doThrow(new RuntimeException()).when(airplaneDao).findByType(null);

        airplaneService.findByType(null);
    }

    @Test
    public void testFindByCapacityMin() throws Exception {
        when(airplaneDao.findByCapacityMin(airplaneCapacity)).thenReturn(Collections.singletonList(airplane));

        List<Airplane> returnedAirplanes = airplaneService.findByCapacityMin(airplaneCapacity);

        Assert.assertEquals(returnedAirplanes, Collections.singletonList(airplane));
        verify(airplaneDao).findByCapacityMin(airplaneCapacity);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testFindByCapacityMin_exceptionHandling() throws Exception {
        doThrow(new IllegalArgumentException()).when(airplaneDao).findByCapacityMin(-1);

        airplaneService.findByCapacityMin(-1);
    }

    @Test
    public void testFindByCapacityMax() throws Exception {
        when(airplaneDao.findByCapacityMax(airplaneCapacity)).thenReturn(Collections.singletonList(airplane));

        List<Airplane> returnedAirplanes = airplaneService.findByCapacityMax(airplaneCapacity);

        Assert.assertEquals(returnedAirplanes, Collections.singletonList(airplane));
        verify(airplaneDao).findByCapacityMax(airplaneCapacity);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testFindByCapacityMax_exceptionHandling() throws Exception {
        doThrow(new IllegalArgumentException()).when(airplaneDao).findByCapacityMax(-1);

        airplaneService.findByCapacityMax(-1);
    }

    @Test
    public void testFindByUsedAfterDateTime() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();

        Airplane airplane2 = new Airplane();
        airplane2.setId(3L);
        airplane2.setName("AirBus");
        airplane2.setType("Tryskac");
        airplane2.setCapacity(5);

        Flight flight1 = new Flight();
        flight1.setId(1L);
        flight1.setDepartureLocation(new Destination("PK", "PKCity"));
        flight1.setArrivalLocation(new Destination("LK", "LKCity"));
        flight1.setDepartureTime(testTime.plusDays(1));
        flight1.setArrivalTime(testTime.plusDays(1).plusHours(1));
        flight1.setAirPlane(airplane);

        List<Airplane> testedAirplanes = Arrays.asList(airplane, airplane2);
        List<Airplane> expectedAirplanes = Arrays.asList(airplane);


        when(flightService.getFlightsSince(testTime)).thenReturn(Arrays.asList(flight1));
        when(airplaneDao.findAll()).thenReturn(testedAirplanes);

        List<Airplane> returnedAirplanes = airplaneService.findByUsedAfterDateTime(testTime);

        Assert.assertEquals(returnedAirplanes, expectedAirplanes);
    }

    @Test
    public void testFindByUsedAfterDateTime_noFlights() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();

        Airplane airplane2 = new Airplane();
        airplane2.setId(3L);
        airplane2.setName("AirBus");
        airplane2.setType("Tryskac");
        airplane2.setCapacity(5);

        List<Airplane> testedAirplanes = Arrays.asList(airplane, airplane2);

        when(flightService.getFlightsSince(testTime)).thenReturn(Collections.emptyList());
        when(airplaneDao.findAll()).thenReturn(testedAirplanes);

        List<Airplane> returnedAirplanes = airplaneService.findByUsedAfterDateTime(testTime);

        Assert.assertEquals(returnedAirplanes, Collections.emptyList());
    }

    @Test
    public void testFindByFreeAfterDateTime() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();

        Airplane airplane2 = new Airplane();
        airplane2.setId(3L);
        airplane2.setName("AirBus");
        airplane2.setType("Tryskac");
        airplane2.setCapacity(5);

        Flight flight1 = new Flight();
        flight1.setId(1L);
        flight1.setDepartureLocation(new Destination("PK", "PKCity"));
        flight1.setArrivalLocation(new Destination("LK", "LKCity"));
        flight1.setDepartureTime(testTime.plusDays(1));
        flight1.setArrivalTime(testTime.plusDays(1).plusHours(1));
        flight1.setAirPlane(airplane);

        List<Airplane> testedAirplanes = Arrays.asList(airplane, airplane2);
        List<Airplane> expectedAirplanes = Arrays.asList(airplane2);


        when(flightService.getFlightsSince(testTime)).thenReturn(Arrays.asList(flight1));
        when(airplaneDao.findAll()).thenReturn(testedAirplanes);

        List<Airplane> returnedAirplanes = airplaneService.findByFreeAfterDateTime(testTime);

        Assert.assertEquals(returnedAirplanes, expectedAirplanes);
    }

    @Test
    public void testFindByFreeAfterDateTime_noFlights() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();

        Airplane airplane2 = new Airplane();
        airplane2.setId(3L);
        airplane2.setName("AirBus");
        airplane2.setType("Tryskac");
        airplane2.setCapacity(5);

        List<Airplane> testedAirplanes = Arrays.asList(airplane, airplane2);

        when(flightService.getFlightsSince(testTime)).thenReturn(Collections.emptyList());
        when(airplaneDao.findAll()).thenReturn(testedAirplanes);

        List<Airplane> returnedAirplanes = airplaneService.findByFreeAfterDateTime(testTime);

        Assert.assertEquals(returnedAirplanes, testedAirplanes);
    }

    @Test
    public void testFindByFreeAfterDateTime_allAirplanesUsed() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();

        Airplane airplane2 = new Airplane();
        airplane2.setId(3L);
        airplane2.setName("AirBus");
        airplane2.setType("Tryskac");
        airplane2.setCapacity(5);

        Flight flight1 = new Flight();
        flight1.setId(1L);
        flight1.setDepartureLocation(new Destination("PK", "PKCity"));
        flight1.setArrivalLocation(new Destination("LK", "LKCity"));
        flight1.setDepartureTime(testTime.plusDays(1));
        flight1.setArrivalTime(testTime.plusDays(1).plusHours(1));
        flight1.setAirPlane(airplane);

        Flight flight2 = new Flight();
        flight2.setId(2L);
        flight2.setDepartureLocation(new Destination("MK", "MKCity"));
        flight2.setArrivalLocation(new Destination("FK", "FKCity"));
        flight2.setDepartureTime(testTime.minusMonths(1));
        flight2.setArrivalTime(testTime.minusMonths(1).plusHours(1));
        flight2.setAirPlane(airplane2);

        List<Airplane> testedAirplanes = Arrays.asList(airplane, airplane2);

        when(flightService.getFlightsSince(testTime)).thenReturn(Arrays.asList(flight1, flight2));
        when(airplaneDao.findAll()).thenReturn(testedAirplanes);

        List<Airplane> returnedAirplanes = airplaneService.findByFreeAfterDateTime(testTime);

        Assert.assertEquals(returnedAirplanes, Collections.emptyList());
    }


}