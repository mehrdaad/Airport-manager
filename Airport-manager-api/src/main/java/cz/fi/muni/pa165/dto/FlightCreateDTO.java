package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Robert Duriancik
 */
public class FlightCreateDTO {
    @NotNull
    private Long departureLocationId;
    @NotNull
    private Long arrivalLocationId;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private List<Long> stewardIds;

    @NotNull
    private Long airplaneId;

    public Long getDepartureLocationId() {
        return departureLocationId;
    }

    public void setDepartureLocationId(Long departureLocationId) {
        this.departureLocationId = departureLocationId;
    }

    public Long getArrivalLocationId() {
        return arrivalLocationId;
    }

    public void setArrivalLocationId(Long arrivalLocationId) {
        this.arrivalLocationId = arrivalLocationId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<Long> getStewardIds() {
        return stewardIds;
    }

    public void setStewardIds(List<Long> stewardIds) {
        this.stewardIds = stewardIds;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightCreateDTO that = (FlightCreateDTO) o;

        if (departureLocationId != null ? !departureLocationId.equals(that.departureLocationId) : that.departureLocationId != null)
            return false;
        if (arrivalLocationId != null ? !arrivalLocationId.equals(that.arrivalLocationId) : that.arrivalLocationId != null)
            return false;
        if (departureTime != null ? !departureTime.equals(that.departureTime) : that.departureTime != null)
            return false;
        return airplaneId != null ? airplaneId.equals(that.airplaneId) : that.airplaneId == null;
    }

    @Override
    public int hashCode() {
        int result = departureLocationId != null ? departureLocationId.hashCode() : 0;
        result = 31 * result + (arrivalLocationId != null ? arrivalLocationId.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (airplaneId != null ? airplaneId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FlightCreateDTO{" +
                "departureLocationId=" + departureLocationId +
                ", arrivalLocationId=" + arrivalLocationId +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", stewardIds=" + stewardIds +
                ", airplaneId=" + airplaneId +
                '}';
    }
}
