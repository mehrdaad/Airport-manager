package cz.fi.muni.pa165.dto;

import java.time.LocalDateTime;

public class FlightDTO {

    private Long id;
    private DestinationDTO departureLocation;
    private DestinationDTO arrivalLocation;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private List<StewardDTO> stewards;
    private AirplaneDTO airplane;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DestinationDTO getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(DestinationDTO departureLocation) {
        this.departureLocation = departureLocation;
    }

    public DestinationDTO getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(DestinationDTO arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public List<StewardDTO> getStewards() {
        return stewards;
    }

    public void setStewards(List<StewardDTO> stewards) {
        this.stewards = stewards;
    }

    public AirplaneDTO getAirplane() {
        return airplane;
    }

    public void setAirplane(AirplaneDTO airplane) {
        this.airplane = airplane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightDTO flightDTO = (FlightDTO) o;

        if (departureLocation != null ? !departureLocation.equals(flightDTO.departureLocation) : flightDTO.departureLocation != null)
            return false;
        if (arrivalLocation != null ? !arrivalLocation.equals(flightDTO.arrivalLocation) : flightDTO.arrivalLocation != null)
            return false;
        if (arrivalTime != null ? !arrivalTime.equals(flightDTO.arrivalTime) : flightDTO.arrivalTime != null)
            return false;
        if (departureTime != null ? !departureTime.equals(flightDTO.departureTime) : flightDTO.departureTime != null)
            return false;
        if (stewards != null ? !stewards.equals(flightDTO.stewards) : flightDTO.stewards != null) return false;
        return airplane != null ? airplane.equals(flightDTO.airplane) : flightDTO.airplane == null;
    }

    @Override
    public int hashCode() {
        int result = departureLocation != null ? departureLocation.hashCode() : 0;
        result = 31 * result + (arrivalLocation != null ? arrivalLocation.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (stewards != null ? stewards.hashCode() : 0);
        result = 31 * result + (airplane != null ? airplane.hashCode() : 0);
        return result;
    }
}
