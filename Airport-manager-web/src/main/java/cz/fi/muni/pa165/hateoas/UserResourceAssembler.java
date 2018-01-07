package cz.fi.muni.pa165.hateoas;

import cz.fi.muni.pa165.controllers.UserRestController;
import cz.fi.muni.pa165.dto.UserDTO;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<UserDTO, UserResource> {
    public UserResourceAssembler() {
        super(UserRestController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(UserDTO userDTO) {
        return new UserResource(userDTO);
    }
}
