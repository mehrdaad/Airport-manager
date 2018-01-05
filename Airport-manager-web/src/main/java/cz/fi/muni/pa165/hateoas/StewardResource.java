package cz.fi.muni.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pa165.dto.StewardDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * @author Ondrej Prikryl
 */
@Relation(value = "steward", collectionRelation = "stewards")
public class StewardResource extends ResourceSupport {

    @JsonProperty("id")
    private Long dtoId;

    private String firstName;
    private String surname;

    public StewardResource(StewardDTO stewardDTO) {
        dtoId = stewardDTO.getId();
        firstName = stewardDTO.getFirstName();
        surname = stewardDTO.getSurname();
    }

    public Long getDtoId() {
        return dtoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }
}
