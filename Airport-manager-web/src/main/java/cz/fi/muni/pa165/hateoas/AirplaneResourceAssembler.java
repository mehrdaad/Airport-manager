package cz.fi.muni.pa165.hateoas;

import cz.fi.muni.pa165.controllers.AirplanesRestController;
import cz.fi.muni.pa165.dto.AirplaneDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;


/**
 * Assembles a HATEOS-compliant representation of a airplane from a AirplaneDTO.
 *
 * @author Jan Cakl
 */

@Component
public class AirplaneResourceAssembler extends ResourceAssemblerSupport<AirplaneDTO, AirplaneResource>  {

    private EntityLinks entityLinks;
    private final static Logger logger = LoggerFactory.getLogger(AirplaneResourceAssembler.class);

    public AirplaneResourceAssembler(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                   @Autowired EntityLinks entityLinks) {
        super(AirplanesRestController.class, AirplaneResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public AirplaneResource toResource(AirplaneDTO airplaneDTO) {
        long id = airplaneDTO.getId();
        AirplaneResource airplaneResource = new AirplaneResource(airplaneDTO);
        try {
            Link self = entityLinks.linkToSingleResource(AirplaneDTO.class, id).withSelfRel();
            airplaneResource.add(self);
        } catch (Exception e) {
            logger.error("Airplane resource error", e);
        }
        return airplaneResource;
    }
}
