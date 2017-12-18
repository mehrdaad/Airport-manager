package cz.fi.muni.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pa165.dto.AirplaneDTO;
import cz.fi.muni.pa165.dto.DestinationDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.dto.StewardDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Flight rendered to JSON. The @Relation annotation specifies its name in HAL rendering of collections.
 *
 * @author Robert Duriancik
 */
@Relation(value = "flight", collectionRelation = "flights")
public class FlightResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private DestinationDTO departureLocation;
    private DestinationDTO arrivalLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<StewardDTO> stewards;
    private AirplaneDTO airplane;

    public FlightResource(FlightDTO flightDTO) {
        dtoId = flightDTO.getId();
        departureLocation = flightDTO.getDepartureLocation();
        arrivalLocation = flightDTO.getArrivalLocation();
        departureTime = flightDTO.getDepartureTime();
        arrivalTime = flightDTO.getArrivalTime();
        stewards = flightDTO.getStewards();
        airplane = flightDTO.getAirplane();
    }

    public long getDtoId() {
        return dtoId;
    }

    public DestinationDTO getDepartureLocation() {
        return departureLocation;
    }

    public DestinationDTO getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public List<StewardDTO> getStewards() {
        return stewards;
    }

    public AirplaneDTO getAirplane() {
        return airplane;
    }
}