package cz.fi.muni.pa165.dto;

/**
 * @author Karel Jiranek
 */
public class StewardDTO {

    private Long id;

    private String surname;

    private String firstName;

    public Long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StewardDTO that = (StewardDTO) o;

        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        return firstName != null ? firstName.equals(that.firstName) : that.firstName == null;
    }

    @Override
    public int hashCode() {
        int result = surname != null ? surname.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Steward{" +
                "first name='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
