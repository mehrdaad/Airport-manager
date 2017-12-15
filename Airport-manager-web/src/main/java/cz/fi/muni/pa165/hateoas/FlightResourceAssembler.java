package cz.fi.muni.pa165.hateoas;

import cz.fi.muni.pa165.controllers.FlightsRestController;
import cz.fi.muni.pa165.dto.FlightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Assembles a HATEOS-compliant representation of a flight from a FlightDTO.
 *
 * @author Robert Duriancik
 */

@Component
public class FlightResourceAssembler extends ResourceAssemblerSupport<FlightDTO, FlightResource> {

    private EntityLinks entityLinks;

    public FlightResourceAssembler(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                   @Autowired EntityLinks entityLinks) {
        super(FlightsRestController.class, FlightResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public FlightResource toResource(FlightDTO flightDTO) {
        long id = flightDTO.getId();
        FlightResource flightResource = new FlightResource(flightDTO);
        try {
            Link self = entityLinks.linkToSingleResource(FlightDTO.class, id).withSelfRel();
            flightResource.add(self);
        } catch (Exception e) {
            // TODO log
        }
        return null;
    }
}
