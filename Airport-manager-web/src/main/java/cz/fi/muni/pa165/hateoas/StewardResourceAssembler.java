package cz.fi.muni.pa165.hateoas;

import cz.fi.muni.pa165.controllers.StewardRestController;
import cz.fi.muni.pa165.dto.StewardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Ondrej Prikryl
 */
@Component
public class StewardResourceAssembler extends ResourceAssemblerSupport<StewardDTO, StewardResource> {

    private final static Logger logger = LoggerFactory.getLogger(StewardResourceAssembler.class);

    public StewardResourceAssembler() {
        super(StewardRestController.class, StewardResource.class);
    }

    @Override
    public StewardResource toResource(StewardDTO stewardDTO) {
        Long id = stewardDTO.getId();
        StewardResource resource = new StewardResource(stewardDTO);
        try {
            resource.add(linkTo(StewardRestController.class).slash(stewardDTO.getId()).withSelfRel());
            Method deleteSteward = StewardRestController.class.getMethod("deleteSteward", long.class);
            resource.add(linkTo(deleteSteward.getDeclaringClass(), deleteSteward, id).withRel("delete"));
        } catch (Exception e) {
            logger.error("Steward resource error", e);
        }
        return resource;
    }
}
