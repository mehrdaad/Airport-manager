package cz.fi.muni.pa165.security;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AirplaneManagerAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserFacade userFacade;

    Logger logger = LoggerFactory.getLogger(AirplaneManagerAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserAuthenticateDTO authenticateDTO = new UserAuthenticateDTO();
        authenticateDTO.setEmail(email);
        authenticateDTO.setPassword(password);

        if (userFacade.authenticate(authenticateDTO)) {
            UserDTO userDTO = userFacade.getUserByEmail(authenticateDTO.getEmail());

            return new UserAuthentication(userDTO);
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
