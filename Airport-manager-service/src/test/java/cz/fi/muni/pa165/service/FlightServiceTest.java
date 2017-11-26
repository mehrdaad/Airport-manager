package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.fail;

public class FlightServiceTest extends BaseServiceTest{
    private Flight flight;

    @Mock
    private FlightDao flightDao;

    @Autowired
    @InjectMocks
    private FlightService flightService;

    @BeforeTest
    public void setUpFlight() {
        LocalDateTime time1 = LocalDateTime.of(2000, 4,1,10,30);
        LocalDateTime time2 = LocalDateTime.of(2000, 5,1,10,30);

        Airplane airplane1 = new Airplane();
        airplane1.setType("Jet");
        airplane1.setName("A-10");
        airplane1.setCapacity(15);

        Airplane airplane2 = new Airplane();
        airplane2.setType("Rocket");
        airplane2.setName("Apollo");
        airplane2.setCapacity(5);

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

        List<Steward> stewards = new ArrayList<>();
        stewards.add(steward1);
        stewards.add(steward2);

        flight = new Flight();
        flight.setAirPlane(airplane1);
        flight.setDepartureLocation(destination1);
        flight.setArrivalLocation(destination2);
        flight.setDepartureTime(time1);
        flight.setArrivalTime(time2);
        flight.setStewards(stewards);
    }

    @Test
    public void addFlightHappyDayScenario() {
        flightService.addFlight(flight);
        verify(flightDao).addFlight(flight);
    }

    @Test
    public void addFlightNull() {
        try {
            flightService.addFlight(null);
            fail("No exception thrown.");
        } catch (DataAccessException e) {
            // OK
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
    }

    @Test
    public void addFlightAfterDateBeforeDate() {
        // TODO handle error
        LocalDateTime before = LocalDateTime.of(2000, 4,1,12,0);
        LocalDateTime after = LocalDateTime.of(2000, 5,1,12,0);
        flight.setDepartureTime(after);
        flight.setArrivalTime(before);
        flightService.addFlight(flight);
    }

    @Test
    public void addFlightEmptyCrew() {
        flight.setStewards(new ArrayList<>());
        flightService.addFlight(flight);
        verify(flightDao).addFlight(flight);
    }

}
