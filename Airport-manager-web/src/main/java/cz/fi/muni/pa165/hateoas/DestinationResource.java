package cz.fi.muni.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.DestinationDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * @author Karel Jirnek
 */
@Relation(value = "destination", collectionRelation = "destinations")
@JsonPropertyOrder({"id", "city", "country"})
public class DestinationResource extends ResourceSupport {

    @JsonProperty("id")
    private Long dtoId;
    private String city;
    private String country;


    public DestinationResource(DestinationDTO dto) {
        this.dtoId = dto.getId();
        this.city = dto.getCity();
        this.country = dto.getCountry();
    }

    public Long getDtoId() {
        return dtoId;
    }

    public void setDtoId(Long dtoId) {
        this.dtoId = dtoId;
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
}
