package cz.fi.muni.pa165.dto;

/**
 *
 * @author Ondřej Přikryl
 * @date 24. 11. 2017
 */
public class DestinationDTO {
    private Long id;
    private String city;
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public int hashCode() {
        final int prime = 31;
        int result = 7;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DestinationDTO)) return false;

        DestinationDTO that = (DestinationDTO) obj;

        if (country != null && !country.equals(that.getCountry())) return false;
        return city != null && city.equals(that.getCity());
    }

    @Override
    public String toString() {
        return "DestinationDTO{" +
                    "country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    '}';
    }
}
