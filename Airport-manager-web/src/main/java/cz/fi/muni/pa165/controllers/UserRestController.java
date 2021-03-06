package cz.fi.muni.pa165.controllers;

import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.hateoas.UserResource;
import cz.fi.muni.pa165.hateoas.UserResourceAssembler;
import cz.fi.muni.pa165.security.SecurityRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private UserResourceAssembler userResourceAssembler;

    public UserRestController(
            @Autowired UserResourceAssembler userResourceAssembler
    ) {
        this.userResourceAssembler = userResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<UserResource> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals(SecurityRoles.ROLE_ANONYMOUS_USER)) {
            return new ResponseEntity<>(new UserResource(new UserDTO()), HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = new UserDTO();
        List<?> authorities = new ArrayList<>(authentication.getAuthorities());
        SimpleGrantedAuthority authority = (SimpleGrantedAuthority) authorities.get(0);

        if (authority.getAuthority().equals(SecurityRoles.ROLE_ADMIN)) {
            userDTO.setAdmin(true);
        } else {
            userDTO.setAdmin(false);
        }

        userDTO.setId((Long) authentication.getPrincipal());
        userDTO.setRegistered((LocalDateTime) authentication.getDetails());
        userDTO.setEmail(((String[]) authentication.getCredentials())[0]);
        userDTO.setPasswordHash(((String[]) authentication.getCredentials())[1]);

        String name = authentication.getName();
        userDTO.setName(name.split(" ")[0]);
        userDTO.setSurname(name.split(" ")[1]);

        UserResource userResource = userResourceAssembler.toResource(userDTO);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }
}
