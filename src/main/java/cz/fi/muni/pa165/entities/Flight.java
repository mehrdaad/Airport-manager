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

    public Flight() {
    }

    /**
     * Instantiate flight with parameters
     *
     * @param departureLocation Place from which flight flies
     * @param arrivalLocation   Place which flight flies to
     * @param arrivalTime       Time when flight land
     * @param departureTime     Time when flight take off
     * @param stewards          Flight crew
     * @param airPlane          Physical aircraft the flight use
     */
    public Flight(Destination departureLocation,
                  Destination arrivalLocation,
                  LocalDateTime arrivalTime,
                  LocalDateTime departureTime,
                  List<Steward> stewards, Airplane airPlane) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stewards = stewards;
        this.airPlane = airPlane;
    }

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
        if (!(o instanceof Flight)) return false;

        Flight that = (Flight) o;

        if (departureTime == null) {
            if (that.getDepartureTime() != null) {
                return false;
            }
        } else {
            if (!departureTime.equals(that.getDepartureTime()))
                return false;
        }

        if (arrivalTime == null) {
            if (that.getArrivalTime() != null) {
                return false;
            }
        } else {
            if (!arrivalTime.equals(that.getArrivalTime()))
                return false;
        }

        if (stewards == null) {
            if (that.getStewards() != null) {
                return false;
            }
        } else {
            if (!stewards.equals(that.getStewards()))
                return false;
        }

        if (airPlane == null) {
            if (that.getAirPlane() != null) {
                return false;
            }
        } else {
            if (!airPlane.equals(that.getAirPlane()))
                return false;
        }


        if (!departureLocation.equals(that.getDepartureLocation()) ||
                !arrivalLocation.equals(that.getArrivalLocation())
                ) {
            return false;
        }
        return true;
    }

    /**
     * Count flight hash code
     *
     * @return Flight hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(this.id);
        result = prime * result + Objects.hashCode(departureTime);
        result = prime * result + Objects.hashCode(departureLocation);
        result = prime * result + Objects.hashCode(arrivalTime);
        result = prime * result + Objects.hashCode(arrivalLocation);
        result = prime * result + Objects.hashCode(airPlane);
        result = prime * result + Objects.hashCode(stewards);
        return result;
    }
}
