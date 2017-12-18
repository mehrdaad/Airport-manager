package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Ondrej Prikryl
 */
public class StewardCreateDTO {

    @NotNull
    private String firstname;

    @NotNull
    private String surname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
