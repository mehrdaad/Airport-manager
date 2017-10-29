package cz.fi.muni.pa165.Entity;


import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * The entity representing a destination.
 * @author Karel Jiranek
 */
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull
    private Destination departureLocation;

    @OneToOne(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull
    private Destination arrivalLocation;

    @Nullable
    private LocalDateTime arrivalTime;

    @Nullable
    private LocalDateTime departueTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @Nullable
    private List<Steward> stawards;

    @Nullable
    @OneToOne(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    private Airplane airPlane;

    public Flight(Destination departureLocation,
                  Destination arrivalLocation,
                  LocalDateTime arrivalTime,
                  LocalDateTime departueTime,
                  List<Steward> stawards, Airplane airPlane) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.arrivalTime = arrivalTime;
        this.departueTime = departueTime;
        this.stawards = stawards;
        this.airPlane = airPlane;
    }

    /**
     * Return entity id
     * @return Id of entity
     */
    public Long getId() {
        return id;
    }

    /**
     * Set entity's id
     * @param id Id to set to the entity
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Destination getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(Destination departureLocation) {
        this.departureLocation = departureLocation;
    }

    public Destination getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(Destination arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartueTime() {
        return departueTime;
    }

    public void setDepartueTime(LocalDateTime departueTime) {
        this.departueTime = departueTime;
    }

    public List<Steward> getStawards() {
        return stawards;
    }

    public void setStawards(List<Steward> stawards) {
        this.stawards = stawards;
    }

    public Airplane getAirPlane() {
        return airPlane;
    }

    public void setAirPlane(Airplane airPlane) {
        this.airPlane = airPlane;
    }


    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Flight:\n")
                .append("Departure location: ").append(departureLocation).append("\n")
                .append("Departure time: ").append(departueTime).append("\n")
                .append("Arrival location: ").append(arrivalLocation).append("\n")
                .append("Arrival time: ").append(arrivalTime).append("\n")
                .append("Airplane: ").append(airPlane);
                //.append("Stewards: ").append(stawards);
        return  strBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;

        Flight that = (Flight) o;

        if (departueTime == null){
            if (that.getDepartueTime() != null){
                return false;
            }
        }
        else{
            if (!departueTime.equals(that.getDepartueTime()))
                return false;
        }

        if (arrivalTime == null){
            if (that.getArrivalTime() != null){
                return false;
            }
        }
        else{
            if (!arrivalTime.equals(that.getArrivalTime()))
                return false;
        }

        if (stawards == null){
            if (that.getStawards() != null){
                return false;
            }
        }
        else{
            if (!stawards.equals(that.getStawards()))
                return false;
        }

        if (airPlane == null){
            if (that.getAirPlane() != null){
                return false;
            }
        }
        else{
            if (!airPlane.equals(that.getAirPlane()))
                return false;
        }


        if(!departureLocation.equals(that.getDepartureLocation()) ||
                !arrivalLocation.equals(that.getArrivalLocation())
                ){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result += prime * result + Objects.hashCode(this.id);
        result += prime * result + Objects.hashCode(departueTime);
        result += prime * result + Objects.hashCode(departureLocation);
        result += prime * result + Objects.hashCode(arrivalTime);
        result += prime * result + Objects.hashCode(arrivalLocation);
        result += prime * result + Objects.hashCode(airPlane);
        result += prime * result + Objects.hashCode(stawards);
        return result;
    }
}
