package cz.fi.muni.pa165.dto;

/**
 * @author Robert Duriancik
 */
public class UserAuthenticateDTO {
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
