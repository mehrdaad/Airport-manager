package cz.fi.muni.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pa165.dto.UserDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Relation(value = "user")
public class UserResource extends ResourceSupport {

    @JsonProperty("id")
    private Long dtoId;

    private String passwordHash;

    private String email;

    private String name;

    private String surname;

    private LocalDateTime registered;

    private boolean isAdmin;

    public UserResource(UserDTO userDTO) {
        this.dtoId = userDTO.getId();
        this.passwordHash = userDTO.getPasswordHash();
        this.email = userDTO.getEmail();
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.registered = userDTO.getRegistered();
        this.isAdmin = userDTO.isAdmin();
    }

    public Long getDtoId() {
        return dtoId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
