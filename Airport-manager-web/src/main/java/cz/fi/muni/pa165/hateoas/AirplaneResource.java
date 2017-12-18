package cz.fi.muni.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pa165.dto.AirplaneDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;


/**
 * Airplane rendered to JSON. The @Relation annotation specifies its name in HAL rendering of collections.
 *
 * @author Jan Cakl
 */


@Relation(value = "airplane", collectionRelation = "airplanes")
public class AirplaneResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;

    private String name;
    private String type;
    private int capacity;

    public AirplaneResource(AirplaneDTO dto) {

        dtoId = dto.getId();
        name = dto.getName();
        type = dto.getType();
        capacity = dto.getCapacity();
    }

    public long getDtoId() {
        return dtoId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

}
