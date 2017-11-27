package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightCreateDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.service.AirplaneService;
import cz.fi.muni.pa165.service.DestinationService;
import cz.fi.muni.pa165.service.FlightService;
import cz.fi.muni.pa165.service.MappingService;
import cz.fi.muni.pa165.service.StewardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the {@link FlightFacade} interface.
 *
 * @author Robert Duriancik
 */

@Service
@Transactional
public class FlightFacadeImpl implements FlightFacade {

//    final static Logger log = LoggerFactory.getLogger(FlightFacadeImpl.class);

    @Inject
    private FlightService flightService;
    @Inject
    private DestinationService destinationService;
    @Inject
    private StewardService stewardService;
    @Inject
    private AirplaneService airplaneService;

    @Inject
    private MappingService mappingService;

    @Override
    public Long createFlight(FlightCreateDTO flightCreateDTO) {
        Flight mappedFlight = mappingService.mapTo(flightCreateDTO, Flight.class);
        flightService.addFlight(mappedFlight);
        return mappedFlight.getId();
    }

    @Override
    public FlightDTO getFlight(Long id) {
        Flight flight = flightService.getFlight(id);
        return flight == null ? null : mappingService.mapTo(flight, FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getFlightsSince(LocalDateTime sinceDateTime) {
        return mappingService.mapTo(flightService.getFlightsSince(sinceDateTime), FlightDTO.class);
    }

    @Override
    public Duration getFlightTime(FlightDTO flightDTO) {
        Flight flight = mappingService.mapTo(flightDTO, Flight.class);
        return flightService.getFlightTime(flight);
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return mappingService.mapTo(flightService.getAllFlights(), FlightDTO.class);
    }

    @Override
    public void deleteFlight(Long flightId) {
        flightService.deleteFlight(flightService.getFlight(flightId));
    }

    @Override
    public void changeArrivalTime(Long flightId, LocalDateTime arrivalTime) {
        Flight flight = flightService.getFlight(flightId);

        if (flight.getDepartureTime().isAfter(arrivalTime)) {
            throw new IllegalArgumentException("Arrival time cannot be before departure time.");
        }

        flight.setArrivalTime(arrivalTime);
//        flightService.updateFlight(flight); // TODO check if it's updated in the persistence storage
    }

    @Override
    public void addStewardToFlight(Long flightId, Long stewardId) {
        flightService.addSteward(
                flightService.getFlight(flightId),
                stewardService.getSteward(stewardId)
        );// TODO check if it's updated in the persistence storage
    }

    @Override
    public void changeAirplane(Long flightId, Long airplaneId) {
        Flight flight = flightService.getFlight(flightId);
        Airplane airplane = airplaneService.findById(airplaneId);
        flight.setAirPlane(airplane);// TODO check if it's updated in the persistence storage
    }
}
