package cz.fi.muni.pa165.hateoas;

import cz.fi.muni.pa165.controllers.StewardRestController;
import cz.fi.muni.pa165.dto.StewardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Ondrej Prikryl
 */
@Component
public class StewardResourceAssembler extends ResourceAssemblerSupport<StewardDTO, StewardResource> {

    private EntityLinks entityLinks;
    private final static Logger logger = LoggerFactory.getLogger(StewardResourceAssembler.class);

    public StewardResourceAssembler(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                    @Autowired EntityLinks entityLinks) {
        super(StewardRestController.class, StewardResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public StewardResource toResource(StewardDTO stewardDTO) {
        Long id = stewardDTO.getId();
        StewardResource resource = new StewardResource(stewardDTO);
        try {
            Link self = entityLinks.linkToSingleResource(StewardDTO.class, id).withSelfRel();
            resource.add(self);
        } catch (Exception e) {
            logger.error("Steward resource error", e);
        }
        return resource;
    }
}
