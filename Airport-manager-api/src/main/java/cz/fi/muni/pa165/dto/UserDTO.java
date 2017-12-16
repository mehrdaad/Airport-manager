package cz.fi.muni.pa165.dto;

import java.time.LocalDateTime;

/**
 * @author Robert Duriancik
 */
public class UserDTO {
    private Long id;

    private String passwordHash;

    private String email;

    private String name;

    private String surname;

    private LocalDateTime registered;

    private boolean isAdmin;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        return email != null ? email.equals(userDTO.email) : userDTO.email == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        return 31 * result + (email != null ? email.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", registered=" + registered +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
