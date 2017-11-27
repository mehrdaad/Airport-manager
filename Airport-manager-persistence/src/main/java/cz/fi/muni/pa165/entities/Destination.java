package cz.fi.muni.pa165.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * The entity representing a destination.
 *
 * @author Robert Duriancik
 */

@Entity
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String country;

    @NotNull
    @Column(nullable = false)
    private String city;

    public Destination() {
    }

    public Destination(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Destination)) return false;

        Destination that = (Destination) o;

        if (country != null && !country.equals(that.getCountry())) return false;
        return city != null && city.equals(that.getCity());
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 1;
        result = 31 * result + (city != null ? city.hashCode() : 1);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
