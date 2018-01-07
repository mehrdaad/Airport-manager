package cz.fi.muni.pa165.security;

import cz.fi.muni.pa165.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class UserAuthentication implements Authentication {

    private UserDTO userDTO;
    private boolean isAuthenticated;

    public UserAuthentication(UserDTO userDTO) {
        this.userDTO = userDTO;
        this.isAuthenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(userDTO.isAdmin() ? SecurityRoles.ROLE_ADMIN : SecurityRoles.ROLE_USER));
    }

    @Override
    public Object getCredentials() {
        return new String[]{userDTO.getEmail(), userDTO.getPasswordHash()};
    }

    @Override
    public Object getDetails() {
        return userDTO.getRegistered();
    }

    @Override
    public Object getPrincipal() {
        return userDTO.getId();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        if (b) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes UserDTO");
        } else {
            isAuthenticated = false;
        }
    }

    @Override
    public String getName() {
        return userDTO.getName() + " " + userDTO.getSurname();
    }
}
