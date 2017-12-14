package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;



/**
 * Test flight entity features
 *
 * @author Karel Jiranek
 */
public class TestFlightDao extends BaseDaoTest {
    @Autowired
    private FlightDao flightDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Flight flight = createFlight("USA", "Czech Republic");
        flightDao.addFlight(flight);
        List<Flight> flights = em.createQuery("select f from Flight f", Flight.class).getResultList();
        Assert.assertEquals(1, flights.size());
        Assert.assertEquals(flight, flights.get(0));
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        List<Flight> selectedFlightList;
        Flight selectedFlight;
        LocalDateTime departureTime = LocalDateTime.of(2018, Month.DECEMBER, 24, 8, 30);
        LocalDateTime arrivalTime = LocalDateTime.of(2018, Month.DECEMBER, 24, 20, 30);

        // Create and persist flight
        Flight createdFlight = createFlight("USA", "Czech Republic");
        flightDao.addFlight(createdFlight);

        // Update flight
        selectedFlightList = em.createQuery("select f from Flight f", Flight.class).getResultList();
        selectedFlight = selectedFlightList.get(0);

        selectedFlight.setDepartureTime(departureTime);
        selectedFlight.setArrivalTime(arrivalTime);
        selectedFlight.getDepartureLocation().setCity("Bratislava");
        selectedFlight.getDepartureLocation().setCountry("Slovakia");
        selectedFlight.getArrivalLocation().setCity("Paris");
        selectedFlight.getArrivalLocation().setCountry("France");
        selectedFlight.getAirPlane().setType("Huge");
        selectedFlight.getAirPlane().setName("My little airoplane");
        selectedFlight.getAirPlane().setCapacity(777);
        selectedFlight.getStewards().get(0).setSurname("Novicky");
        selectedFlight.getStewards().get(0).setFirstName("Mike");
        flightDao.updateFlight(selectedFlight);

        // Test flight
        selectedFlightList = em.createQuery("select f from Flight f", Flight.class).getResultList();
        selectedFlight = selectedFlightList.get(0);
        Assert.assertEquals(1, selectedFlightList.size());
        Assert.assertEquals("Bratislava", selectedFlight.getDepartureLocation().getCity());
        Assert.assertEquals("Slovakia", selectedFlight.getDepartureLocation().getCountry());
        Assert.assertEquals("Paris", selectedFlight.getArrivalLocation().getCity());
        Assert.assertEquals("France", selectedFlight.getArrivalLocation().getCountry());
        Assert.assertEquals(arrivalTime, selectedFlight.getArrivalTime());
        Assert.assertEquals(777, selectedFlight.getAirPlane().getCapacity());
        Assert.assertEquals("My little airoplane", selectedFlight.getAirPlane().getName());
        Assert.assertEquals("Mike", selectedFlight.getStewards().get(0).getFirstName());
        Assert.assertEquals("Novicky", selectedFlight.getStewards().get(0).getSurname());

    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        List<Flight> selectedFlightList;
        Flight selectedFlight;

        // Create and persist flight
        Flight createdFlight = createFlight("USA", "Czech Republic");
        flightDao.addFlight(createdFlight);

        // Delete flight
        flightDao.deleteFlight(createdFlight);

        // Test flight
        selectedFlightList = em.createQuery("select f from Flight f", Flight.class).getResultList();
        Assert.assertEquals(0, selectedFlightList.size());
    }

    @Test
    @Transactional
    public void testFindById() throws Exception {
        // Create and persist flight
        Flight createdFlight = createFlight("USA", "Czech Republic");
        flightDao.addFlight(createdFlight);
        Long id = createdFlight.getId();

        // Find by id
        Flight selectedFlight = flightDao.getFlight(id);

        // Test ids
        Assert.assertEquals(selectedFlight.getId(), createdFlight.getId());
    }

    @Test
    @Transactional
    public void testFindAll() throws Exception {
        Flight flight1 = createFlight("USA", "Czech Republic");
        Flight flight2 = createFlight("USA", "Zimbabwe");
        Flight flight3 = createFlight("China", "Czech Republic");

        flightDao.addFlight(flight1);
        flightDao.addFlight(flight2);
        flightDao.addFlight(flight3);

        List<Flight> allFlights = flightDao.getAllFlights();

        Assert.assertEquals(3, allFlights.size());
        Assert.assertTrue(allFlights.contains(flight1)
                && allFlights.contains(flight2)
                && allFlights.contains(flight3));

    }

    @Test
    public void testHashCode() {
        Flight createdFlight1 = createFlight("USA", "Czech Republic");
        Flight createdFlight2 = createFlight("USA", "Czech Republic");
        Assert.assertEquals(createdFlight1.hashCode(), createdFlight2.hashCode());
    }

    @Test
    public void testEquals() {
        Flight createdFlight1 = createFlight("USA", "Czech Republic");
        Flight createdFlight2 = createFlight("USA", "Czech Republic");
        Flight createdFlight3 = createFlight("China", "Czech Republic");
        Assert.assertFalse(createdFlight1.equals(createdFlight3));
        Assert.assertTrue(createdFlight1.equals(createdFlight2));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testCreateNullFlight(){
        flightDao.addFlight(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testUpdateNullFlight(){
        flightDao.updateFlight(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testDeleteNullFlight(){
        flightDao.deleteFlight(null);
    }

    private static Flight createFlight(String arrivalState, String departureState) {
        // Create destinations
        String arrivalCityName = "UNKOWN";
        String departureCityName = "UNKOWN";

        Destination arrivalDestination = new Destination();
        arrivalDestination.setCity(arrivalCityName);
        arrivalDestination.setCountry(arrivalState);

        Destination departureDestination = new Destination();
        departureDestination.setCity(departureCityName);
        departureDestination.setCountry(departureState);

        // Create stewards
        Steward steward = new Steward();
        steward.setFirstName("John");
        steward.setSurname("Dail");
        List<Steward> stewards = new LinkedList<>();
        stewards.add(steward);

        // Create times
        LocalDateTime departureTime = LocalDateTime.of(2017, Month.DECEMBER, 24, 8, 30);
        LocalDateTime arrivalTime = LocalDateTime.of(2017, Month.DECEMBER, 24, 20, 30);

        // Create airplane
        Airplane airPlane = new Airplane();
        airPlane.setCapacity(100);
        airPlane.setName("Boeing 737");
        airPlane.setType("Basic");

        Flight flight = new Flight();
        flight.setArrivalLocation(arrivalDestination);
        flight.setDepartureLocation(departureDestination);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flight.setStewards(stewards);
        flight.setAirPlane(airPlane);
        return flight;
    }

}

