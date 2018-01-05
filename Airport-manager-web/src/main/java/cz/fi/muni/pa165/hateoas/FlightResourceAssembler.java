package cz.fi.muni.pa165.hateoas;

import cz.fi.muni.pa165.controllers.FlightsRestController;
import cz.fi.muni.pa165.dto.FlightDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Assembles a HATEOS-compliant representation of a flight from a FlightDTO.
 *
 * @author Robert Duriancik
 */

@Component
public class FlightResourceAssembler extends ResourceAssemblerSupport<FlightDTO, FlightResource> {

    private final static Logger logger = LoggerFactory.getLogger(FlightResourceAssembler.class);

    public FlightResourceAssembler() {
        super(FlightsRestController.class, FlightResource.class);
    }

    @Override
    public FlightResource toResource(FlightDTO flightDTO) {
        long id = flightDTO.getId();
        FlightResource flightResource = new FlightResource(flightDTO);
        try {
            flightResource.add(linkTo(FlightsRestController.class).slash(flightDTO.getId()).withSelfRel());

            Method deleteFlight = FlightsRestController.class.getMethod("deleteFlight", long.class);
            flightResource.add(linkTo(deleteFlight.getDeclaringClass(), deleteFlight, id).withRel("delete"));
        } catch (Exception e) {
            logger.error("FlightResource error", e);
        }
        return flightResource;
    }
}
