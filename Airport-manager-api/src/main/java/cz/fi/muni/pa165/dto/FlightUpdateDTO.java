package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class FlightUpdateDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long departureLocationId;
    @NotNull
    private Long arrivalLocationId;
    @NotNull
    private LocalDateTime departureTime;
    @NotNull
    private LocalDateTime arrivalTime;
    @NotNull
    private Long airplaneId;
    private List<Long> stewardIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
    }

    public List<Long> getStewardIds() {
        return stewardIds;
    }

    public void setStewardIds(List<Long> stewardIds) {
        this.stewardIds = stewardIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightUpdateDTO that = (FlightUpdateDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (departureLocationId != null ? !departureLocationId.equals(that.departureLocationId) : that.departureLocationId != null)
            return false;
        if (arrivalLocationId != null ? !arrivalLocationId.equals(that.arrivalLocationId) : that.arrivalLocationId != null)
            return false;
        if (departureTime != null ? !departureTime.equals(that.departureTime) : that.departureTime != null)
            return false;
        if (arrivalTime != null ? !arrivalTime.equals(that.arrivalTime) : that.arrivalTime != null) return false;
        if (airplaneId != null ? !airplaneId.equals(that.airplaneId) : that.airplaneId != null) return false;
        return stewardIds != null ? stewardIds.equals(that.stewardIds) : that.stewardIds == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (departureLocationId != null ? departureLocationId.hashCode() : 0);
        result = 31 * result + (arrivalLocationId != null ? arrivalLocationId.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (airplaneId != null ? airplaneId.hashCode() : 0);
        result = 31 * result + (stewardIds != null ? stewardIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FlightUpdateDTO{" +
                "id=" + id +
                ", departureLocationId=" + departureLocationId +
                ", arrivalLocationId=" + arrivalLocationId +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", airplaneId=" + airplaneId +
                ", stewardIds=" + stewardIds +
                '}';
    }
}
