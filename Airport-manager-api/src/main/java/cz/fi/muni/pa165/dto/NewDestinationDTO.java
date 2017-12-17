package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class NewDestinationDTO {

    private Long id;

    @NotNull
    private String country;

    @NotNull
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewDestinationDTO that = (NewDestinationDTO) o;
        return Objects.equals(city, that.city) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
