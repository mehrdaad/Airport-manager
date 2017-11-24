package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.FlightCreateDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.facade.FlightFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FlightFacadeImpl implements FlightFacade {

    final static Logger log = LoggerFactory.getLogger(FlightFacadeImpl.class);

    @Inject
    private FlightService flightService;
    @Inject
    private DestinationService destinationService;
    @Inject
    private StewardService stewardService;
    @Inject
    private AirplaneService airplaneService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void createFlight(FlightCreateDTO flightCreateDTO) {
        Flight mappedFlight = beanMappingService.mapTo(flightCreateDTO, Flight.class);
        flightService.addFlight(mappedFlight);
    }

    @Override
    public List<FlightDTO> getFlightsBySteward(StewardDTO stewardDTO) {
        return null;
    }

    @Override
    public List<FlightDTO> getFlightsByDestination(DestinationDTO destinationDTO) {
        return null;
    }

    @Override
    public List<FlightDTO> getFlightsByAirplane(AirplaneDTO airplaneDTO) {
        return null;
    }

    @Override
    public FlightDTO getFlight(Long id) {
        Flight flight = flightService.getFlight(id);
        return flight == null ? null : beanMappingService.mapTo(flight, FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getFlightsSince(LocalDateTime sinceDateTime) {
        return beanMappingService.mapTo(flightService.getFlightsSince(sinceDateTime), FlightDTO.class);
    }

    @Override
    public Duration getFlightTime(FlightDTO flightDTO) {
        Flight flight = beanMappingService.mapTo(flightDTO, Flight.class);
        return flightService.getFlightTime(flight);
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return beanMappingService.mapTo(flightService.getAllFlights(), FlightDTO.class);
    }

    @Override
    public void deleteFlight(Long flightId) {
        flightService.deleteFlight(flightService.getFlight(flightId));
    }

    @Override
    public void changeArrivalTime(Long flightId, LocalDateTime arrivalTime) {
        Flight flight = flightService.getFlight(flightId);

        if (flight.getDepartureTime().isAfter(arrivalTime)) {
            throw new IllegalArgumentException("Arrival time cannot be before departure time.")
        }

        flight.setArrivalTime(arrivalTime);
        flightService.updateFlight(flight);
    }

    @Override
    public void addSteward(Long flightId, Long stewardId) {
        flightService.addSteward(
                flightService.getFlight(flightId),
                stewardService.getSteward(stewardId)
        );
    }

    @Override
    public void changeAirplane(Long flightId, Long airplaneId) {

    }
}
