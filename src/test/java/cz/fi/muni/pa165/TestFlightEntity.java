package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * Test flight entity features
 *
 * @author Karel Jiranek
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestFlightEntity extends BaseDaoTest {
    @Autowired
    private FlightDao flightDao;


    private Flight flight = new Flight();

    @Test(expected = IllegalArgumentException.class)
    public void testSetArrivalLocationNull() {
        flight.setArrivalLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDepartureLocationNull() {
        flight.setDepartureLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDepartureTimeNull() {
        flight.setDepartureTime(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetArrivalTimeNull() {
        flight.setArrivalTime(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStewardsNull() {
        flight.setStewards(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStewardsContainsNull() {
        List<Steward> stewards = new ArrayList<>();
        stewards.add(null);
        flight.setStewards(stewards);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetAirPlaneNull() {
        flight.setAirPlane(null);
    }

}

