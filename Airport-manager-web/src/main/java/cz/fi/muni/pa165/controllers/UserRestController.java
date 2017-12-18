package cz.fi.muni.pa165.controllers;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.exceptions.InvalidRequestException;
import cz.fi.muni.pa165.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.hateoas.UserResource;
import cz.fi.muni.pa165.hateoas.UserResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private UserFacade userFacade;
    private UserResourceAssembler userResourceAssembler;

    public UserRestController(
            @Autowired UserFacade userFacade,
            @Autowired UserResourceAssembler userResourceAssembler
    ) {
        this.userFacade = userFacade;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<UserResource> authenticateUser(@RequestBody @Valid UserAuthenticateDTO userAuthenticateDTO,
                                                           BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Failed validation");
        }

        boolean result = userFacade.authenticate(userAuthenticateDTO);
        if (result) {
            UserDTO userDTO = userFacade.getUserByEmail(userAuthenticateDTO.getEmail());
            UserResource userResource = userResourceAssembler.toResource(userDTO);
            return new ResponseEntity<>(userResource, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }
}
