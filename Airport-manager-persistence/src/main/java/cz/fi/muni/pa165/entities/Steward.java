package cz.fi.muni.pa165.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Class representing Steward entity.
 *
 * @author Ondrej Prikryl
 */
@Entity
public class Steward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String surname;

    @NotNull
    @Column(nullable = false)
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
        if (!(o instanceof Steward)) return false;

        Steward steward = (Steward) o;

        if (!getSurname().equals(steward.getSurname())) return false;
        return getFirstName().equals(steward.getFirstName());
    }

    @Override
    public int hashCode() {
        int result = getSurname().hashCode();
        result = 31 * result + getFirstName().hashCode();
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
