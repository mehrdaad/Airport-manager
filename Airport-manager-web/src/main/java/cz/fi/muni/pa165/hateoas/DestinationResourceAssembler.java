package cz.fi.muni.pa165.hateoas;

import cz.fi.muni.pa165.controllers.DestinationsRestController;
import cz.fi.muni.pa165.dto.DestinationDTO;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Karel Jiranek
 */
@Component
public class DestinationResourceAssembler extends ResourceAssemblerSupport<DestinationDTO, DestinationResource> {

    public DestinationResourceAssembler() {
        super(DestinationsRestController.class, DestinationResource.class);
    }

    @Override
    public DestinationResource toResource(DestinationDTO destinationDTO) {
        long id = destinationDTO.getId();
        DestinationResource productResource = new DestinationResource(destinationDTO);
        try {
            productResource.add(linkTo(DestinationsRestController.class).slash(destinationDTO.getId()).withSelfRel());

            Method removeDestination = DestinationsRestController.class.getMethod("removeDestination", long.class);
            productResource.add(linkTo(removeDestination.getDeclaringClass(),removeDestination, id).withRel("delete"));
        } catch (Exception ex) {
        }
        return productResource;
    }
}
