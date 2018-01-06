package cz.fi.muni.pa165.hateoas;

import cz.fi.muni.pa165.controllers.AirplanesRestController;
import cz.fi.muni.pa165.dto.AirplaneDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * Assembles a HATEOS-compliant representation of a airplane from a AirplaneDTO.
 *
 * @author Jan Cakl
 */

@Component
public class AirplaneResourceAssembler extends ResourceAssemblerSupport<AirplaneDTO, AirplaneResource>  {

    private final static Logger logger = LoggerFactory.getLogger(AirplaneResourceAssembler.class);

    public AirplaneResourceAssembler() {
        super(AirplanesRestController.class, AirplaneResource.class);
    }

    @Override
    public AirplaneResource toResource(AirplaneDTO airplaneDTO) {
        long id = airplaneDTO.getId();
        AirplaneResource airplaneResource = new AirplaneResource(airplaneDTO);
        try {
            airplaneResource.add(linkTo(AirplanesRestController.class).slash(airplaneDTO.getId()).withSelfRel());
            Method deleteAirplane = AirplanesRestController.class.getMethod("deleteAirplane", long.class);
            airplaneResource.add(linkTo(deleteAirplane.getDeclaringClass(), deleteAirplane, id).withRel("delete"));
        } catch (Exception e) {
            logger.error("Airplane resource error", e);
        }
        return airplaneResource;
    }
}
