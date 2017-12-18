package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;



/**
 * Test flight entity features
 *
 * @author Karel Jiranek
 */
public class FlightEntityTest extends BaseDaoTest {
    @Autowired
    private FlightDao flightDao;


    private Flight flight = new Flight();

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetArrivalLocationNull() {
        flight.setArrivalLocation(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetDepartureLocationNull() {
        flight.setDepartureLocation(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetDepartureTimeNull() {
        flight.setDepartureTime(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetArrivalTimeNull() {
        flight.setArrivalTime(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetStewardsNull() {
        flight.setStewards(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetStewardsContainsNull() {
        List<Steward> stewards = new ArrayList<>();
        stewards.add(null);
        flight.setStewards(stewards);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetAirPlaneNull() {
        flight.setAirplane(null);
    }

}

