package cz.muni.fi.pa165.sampledata;

import cz.fi.muni.pa165.entities.*;
import cz.fi.muni.pa165.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karel Jiranek
 * @author Ondrej Prikryl
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    @Autowired
    private DestinationService  destinationService;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private StewardService stewardService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private UserService userService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() {

        User admin = new User();
        admin.setAdmin(true);
        admin.setEmail("admin@gmail.com");
        admin.setName("Tonda");
        admin.setSurname("Admin");
        admin.setRegistered(LocalDateTime.now().minusYears(2));
        userService.addUser(admin, "heslo");

        User steward1 = new User();
        steward1.setAdmin(false);
        steward1.setEmail("steward.oliver@gmail.com");
        steward1.setName("Oliver");
        steward1.setSurname("Smith");
        steward1.setRegistered(LocalDateTime.now().minusYears(1));
        userService.addUser(steward1, "heslo");

        Destination boston = destination("USA", "Boston");
        Destination berlin = destination("Germany", "Berlin");
        Destination wroclaw = destination("Poland", "Wroclaw");
        Destination thimphu = destination("Bhutan", "Thimphu");
        Destination sucre = destination("Bolivia", "Sucre");
        Destination brasilia = destination("Brazil", "Brasilia");
        Destination sofia = destination("Bulgaria", "Sofia");
        Destination roseau = destination("Dominica", "Roseau");
        Destination cairo = destination("Egypt", "Cairo");
        Destination suva = destination("Fiji", "Suva");

        Airplane boeing777 = airplane("Commercial", "Boeing 777", 427);
        Airplane boeing787 = airplane("Commercial", "Boeing 787", 310);
        Airplane airbus380 = airplane("Commercial", "Airbus A380", 868);
        Airplane airbus350 = airplane("Commercial", "Airbus A350", 550);
        Airplane g650 = airplane("Private", "Gulstream G650", 18);
        Airplane el1000 = airplane("Private", "Embraer Lineage 1000", 19);
        Airplane airForceOne = airplane("Air Force One", "Boeing VC-25", 76);

        Steward harry = steward("Harry", "Doe");
        Steward oliver = steward("Oliver", "Smith");
        Steward charlie = steward("Charlie", "Bolton");
        Steward james = steward("James", "Chase");
        Steward lucas = steward("Lucas", "Dickson");
        Steward jake = steward("Jake", "Cock");
        Steward olivia = steward("Olivia", "Eliott");
        Steward emily = steward("Emily", "Foster");
        Steward jessica = steward("Jessica", "Green");
        Steward chloe = steward("Chloe", "Fletcher");
        Steward sara = steward("Sara", "Jenkins");
        Steward deleteTest = steward("Delete", "Test");

        List<Steward> stewards1 = new ArrayList<>();
        List<Steward> stewards2 = new ArrayList<>();
        List<Steward> stewards3 = new ArrayList<>();
        List<Steward> stewards4 = new ArrayList<>();

        stewards1.add(harry);
        stewards1.add(james);
        stewards1.add(olivia);
        stewards1.add(chloe);

        stewards2.add(oliver);
        stewards2.add(lucas);
        stewards2.add(emily);
        stewards2.add(sara);

        stewards3.add(charlie);
        stewards3.add(jake);
        stewards3.add(jessica);

        stewards4.add(harry);
        stewards4.add(oliver);
        stewards4.add(emily);
        stewards4.add(chloe);

        Flight presentFlight1 = flight(boeing777, stewards1, boston, berlin, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(10));
        Flight presentFlight2 = flight(airForceOne, stewards2, boston, brasilia, LocalDateTime.now().minusHours(5), LocalDateTime.now().plusHours(2));
        Flight presentFlight3 = flight(g650, stewards3, wroclaw, thimphu, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(10));

        Flight pastFlight1 = flight(airbus350, stewards2, cairo, sofia, LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(10).plusHours(5));
        Flight pastFlight2 = flight(boeing787, stewards4, cairo, sofia, LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(10).plusHours(5));
        Flight pastFlight3 = flight(airbus380, stewards1, cairo, sofia, LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(10).plusHours(5));

        Flight futureFlight1 = flight(el1000, stewards3, thimphu, roseau, LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(10).plusHours(10));
        Flight futureFlight2 = flight(el1000, stewards1, wroclaw, sofia, LocalDateTime.now().plusDays(9), LocalDateTime.now().plusDays(9).plusHours(2));
        Flight futureFlight3 = flight(g650, stewards4, sucre, brasilia, LocalDateTime.now().plusDays(10).minusHours(2), LocalDateTime.now().plusDays(10).plusHours(1));
    }

    private Destination destination(String country, String city) {
        Long id = destinationService.createDestination(country, city);
        return destinationService.getDestinationById(id);
    }

    private Steward steward(String firstname, String surname) {
        Long id = stewardService.createSteward(firstname, surname);
        return stewardService.getSteward(id);
    }

    private Airplane airplane(String type, String name, int capacity) {
        Airplane airplane = new Airplane();
        airplane.setType(type);
        airplane.setName(name);
        airplane.setCapacity(capacity);

        Long id = airplaneService.addAirplane(airplane);
        return airplaneService.findById(id);
    }

    private Flight flight(Airplane airplane, List<Steward> stewards, Destination departure,
                          Destination arrival, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        Flight flight = new Flight();
        flight.setAirplane(airplane);
        flight.setStewards(stewards);
        flight.setDepartureLocation(departure);
        flight.setArrivalLocation(arrival);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);

        Long id = flightService.addFlight(flight);
        return flightService.getFlight(id);
    }
}
