package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Ondrej Prikryl
 */
public class StewardCreateDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String surname;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
