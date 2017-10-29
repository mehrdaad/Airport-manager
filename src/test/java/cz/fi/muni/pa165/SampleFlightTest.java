package cz.fi.muni.pa165;

import cz.fi.muni.pa165.Dao.FlightDao;
import cz.fi.muni.pa165.Entity.Airplane;
import cz.fi.muni.pa165.Entity.Destination;
import cz.fi.muni.pa165.Entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/**
 * The entity representing a destination.
 * @author Karel Jiranek
 */
public class SampleFlightTest extends BaseDaoTest {
    @Autowired
    private FlightDao flightDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void createTest() throws Exception {
        Flight flight = createFlight("USA", "Czech Republic");
        flightDao.create(flight);
        List<Flight> flights =  em.createQuery("select f from Flight f", Flight.class).getResultList();
        Assert.assertEquals(1, flights.size());
        Assert.assertEquals(flight, flights.get(0));
    }

    @Test
    @Transactional
    public void updateTest() throws Exception {
        List<Flight> selectedFlightList;
        Flight selectedFlight;
        LocalDateTime departureTime = LocalDateTime.of(2018, Month.DECEMBER, 24, 8, 30);
        LocalDateTime arrivalTime = LocalDateTime.of(2018, Month.DECEMBER, 24, 20, 30);

        // Create and persist flight
        Flight createdFlight = createFlight("USA", "Czech Republic");
        flightDao.create(createdFlight);

        // Update flight
        selectedFlightList =  em.createQuery("select f from Flight f", Flight.class).getResultList();
        selectedFlight = selectedFlightList.get(0);

        selectedFlight.setDepartueTime(departureTime);
        selectedFlight.setArrivalTime(arrivalTime);
        selectedFlight.getDepartureLocation().setCity("Bratislava");
        selectedFlight.getDepartureLocation().setCountry("Slovakia");
        selectedFlight.getArrivalLocation().setCity("Paris");
        selectedFlight.getArrivalLocation().setCountry("France");
        selectedFlight.getAirPlane().setType("Huge");
        selectedFlight.getAirPlane().setName("My little airoplane");
        selectedFlight.getAirPlane().setCapacity(777);
        flightDao.update(selectedFlight);

        // Test flight
        selectedFlightList =  em.createQuery("select f from Flight f", Flight.class).getResultList();
        selectedFlight = selectedFlightList.get(0);
        Assert.assertEquals(1, selectedFlightList.size());
        Assert.assertEquals("Bratislava", selectedFlight.getDepartureLocation().getCity());
        Assert.assertEquals("Slovakia", selectedFlight.getDepartureLocation().getCountry());
        Assert.assertEquals("Paris", selectedFlight.getArrivalLocation().getCity());
        Assert.assertEquals("France", selectedFlight.getArrivalLocation().getCountry());
        Assert.assertEquals(arrivalTime, selectedFlight.getArrivalTime());
        Assert.assertEquals(777, selectedFlight.getAirPlane().getCapacity());
        Assert.assertEquals("My little airoplane", selectedFlight.getAirPlane().getName());
        Assert.assertEquals("Huge", selectedFlight.getAirPlane().getType());
    }

    @Test
    @Transactional
    public void deleteTest() throws Exception {
        List<Flight> selectedFlightList;
        Flight selectedFlight;

        // Create and persist flight
        Flight createdFlight = createFlight("USA", "Czech Republic");
        flightDao.create(createdFlight);

        // Delete flight
        flightDao.delete(createdFlight);

        // Test flight
        selectedFlightList =  em.createQuery("select f from Flight f", Flight.class).getResultList();
        Assert.assertEquals(0, selectedFlightList.size());
    }

    @Test
    @Transactional
    public void findByIdTest() throws Exception {
        // Create and persist flight
        Flight createdFlight = createFlight("USA", "Czech Republic");
        flightDao.create(createdFlight);
        Long id = createdFlight.getId();

        // Find by id
        Flight selectedFlight = flightDao.findById(id);

        // Test ids
        Assert.assertEquals(selectedFlight.getId(), createdFlight.getId());
    }

    @Test
    @Transactional
    public void testFindAll() throws Exception {
        Flight flight1 = createFlight("USA", "Czech Republic");
        Flight flight2 = createFlight("USA", "Zimbabwe");
        Flight flight3 = createFlight("China", "Czech Republic");

        flightDao.create(flight1);
        flightDao.create(flight2);
        flightDao.create(flight3);

        List<Flight> allFlights = flightDao.findAll();

        Assert.assertEquals(3, allFlights.size());
        Assert.assertTrue(allFlights.contains(flight1)
                && allFlights.contains(flight2)
                && allFlights.contains(flight3));

    }

    @Test
    public void toStringTest(){
        String expectedOutput = "Flight:\n" +
                "Departure location: Destination{country='USA', city='UNKOWN'}\n" +
                "Departure time: 2017-12-24T08:30\n" +
                "Arrival location: Destination{country='Czech Republic', city='UNKOWN'}\n" +
                "Arrival time: 2017-12-24T20:30\n" +
                "Airplane: Airplane{name='Boeing 737', type='Basic', capacity='100'}";

        Flight createdFlight = createFlight("USA", "Czech Republic");
        Assert.assertEquals(expectedOutput, createdFlight.toString());
    }

    @Test
    public void testHashCode(){
        Flight createdFlight1 = createFlight("USA", "Czech Republic");
        Flight createdFlight2 = createFlight("USA", "Czech Republic");
        Assert.assertEquals(createdFlight1.hashCode(), createdFlight2.hashCode());
    }

    @Test
    public void testEquals(){
        Flight createdFlight1 = createFlight("USA", "Czech Republic");
        Flight createdFlight2 = createFlight("USA", "Czech Republic");
        Flight createdFlight3 = createFlight("China", "Czech Republic");
        Assert.assertFalse(createdFlight1.equals(createdFlight3));
        Assert.assertTrue(createdFlight1.equals(createdFlight2));
    }

    private Flight createFlight(String arrivalState, String departureState){
        // Create destinations
        String arrivalCityName = "UNKOWN";
        String departureCtiyName = "UNKOWN";

        Destination arrivalDestination = new Destination();
        arrivalDestination.setCity(arrivalCityName);
        arrivalDestination.setCountry(arrivalState);

        Destination departueDestination = new Destination();
        departueDestination.setCity(departureCtiyName);
        departueDestination.setCountry(departureState);

        // Create times
        LocalDateTime departureTime = LocalDateTime.of(2017, Month.DECEMBER, 24, 8, 30);
        LocalDateTime arrivalTime = LocalDateTime.of(2017, Month.DECEMBER, 24, 20, 30);

        // Create airplane
        Airplane airPlane = new Airplane();
        airPlane.setCapacity(100);
        airPlane.setName("Boeing 737");
        airPlane.setType("Basic");

        return new Flight(arrivalDestination, departueDestination, arrivalTime, departureTime, null, airPlane);
    }

}

