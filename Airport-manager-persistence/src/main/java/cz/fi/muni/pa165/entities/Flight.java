package cz.fi.muni.pa165.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * The entity representing a flight.
 *
 * @author Karel Jiranek
 */
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull
    private Destination departureLocation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull
    private Destination arrivalLocation;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Steward> stewards;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Airplane airPlane;

    /**
     * Return entity id
     *
     * @return Id of entity
     */
    public Long getId() {
        return id;
    }

    /**
     * Set entity's id
     *
     * @param id Id to set to the entity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get departure location
     *
     * @return Departure location
     */
    public Destination getDepartureLocation() {
        return departureLocation;
    }

    /**
     * Set departure location
     *
     * @param departureLocation Location to set
     */
    public void setDepartureLocation(Destination departureLocation) {
        if (departureLocation == null){
            throw new IllegalArgumentException("DepartureLocation set DepartureTime to null.");
        }
        this.departureLocation = departureLocation;
    }

    /**
     * Get arrival location
     *
     * @return Arrival location
     */
    public Destination getArrivalLocation() {
        return arrivalLocation;
    }

    /**
     * Set arrival location
     *
     * @param arrivalLocation Location to set
     */
    public void setArrivalLocation(Destination arrivalLocation) {
        if (arrivalLocation == null){
            throw new IllegalArgumentException("ArrivalLocation set DepartureTime to null.");
        }
        this.arrivalLocation = arrivalLocation;
    }

    /**
     * Get arrival time
     *
     * @return Arrival time
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Set arrival time
     *
     * @param arrivalTime Flight arrival time to set
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        if (arrivalTime == null){
            throw new IllegalArgumentException("ArrivalTime set DepartureTime to null.");
        }
        this.arrivalTime = arrivalTime;
    }

    /**
     * Get flight departure time
     *
     * @return Flight departure time
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Set flight departure time
     *
     * @param departureTime Flight departure to set
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        if (departureTime == null){
            throw new IllegalArgumentException("Cannot set DepartureTime to null.");
        }
        this.departureTime = departureTime;
    }

    /**
     * Get flight stewards
     *
     * @return Flight stewards
     */
    public List<Steward> getStewards() {
        return stewards;
    }

    /**
     * Set stewards for flight
     *
     * @param stewards Stewards to set for flight
     */
    public void setStewards(List<Steward> stewards) {
        if (stewards == null){
            throw new IllegalArgumentException("Cannot set stewards to null.");
        }
        if (stewards.contains(null)){
            throw new IllegalArgumentException("Cannot set stewards with null values");
        }
        this.stewards = stewards;
    }

    /**
     * Get flight airplane
     *
     * @return Flight airplane
     */
    public Airplane getAirPlane() {
        return airPlane;
    }

    /**
     * Set flight airplane
     *
     * @param airPlane Flight airplane
     */
    public void setAirPlane(Airplane airPlane) {
        if (airPlane == null){
            throw new IllegalArgumentException("Cannot set airplane to null.");
        }
        this.airPlane = airPlane;
    }


    /**
     * Convert flight to string format
     *
     * @return Flight as string
     */
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Flight:\n")
                .append("Departure location: ").append(departureLocation).append("\n")
                .append("Departure time: ").append(departureTime).append("\n")
                .append("Arrival location: ").append(arrivalLocation).append("\n")
                .append("Arrival time: ").append(arrivalTime).append("\n")
                .append("Airplane: ").append(airPlane);
        //.append("Stewards: ").append(stawards);
        return strBuilder.toString();
    }

    /**
     * Check if objects are equal
     *
     * @param o Object to check
     * @return True if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (departureLocation != null ? !departureLocation.equals(flight.departureLocation) : flight.departureLocation != null)
            return false;
        if (arrivalLocation != null ? !arrivalLocation.equals(flight.arrivalLocation) : flight.arrivalLocation != null)
            return false;
        if (arrivalTime != null ? !arrivalTime.equals(flight.arrivalTime) : flight.arrivalTime != null) return false;
        if (departureTime != null ? !departureTime.equals(flight.departureTime) : flight.departureTime != null)
            return false;
        if (stewards != null ? !stewards.equals(flight.stewards) : flight.stewards != null) return false;
        return airPlane != null ? airPlane.equals(flight.airPlane) : flight.airPlane == null;
    }

    /**
     * Count flight hash code
     *
     * @return Flight hash code
     */
    @Override
    public int hashCode() {
        int result = departureLocation != null ? departureLocation.hashCode() : 0;
        result = 31 * result + (arrivalLocation != null ? arrivalLocation.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (stewards != null ? stewards.hashCode() : 0);
        result = 31 * result + (airPlane != null ? airPlane.hashCode() : 0);
        return result;
    }


}
