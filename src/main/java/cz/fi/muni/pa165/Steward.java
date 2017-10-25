package cz.fi.muni.pa165;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class representing Steward entity.
 *
 * @author Ondrej Prikryl
 */
@Entity
@Table(name="Steward")
@NamedQuery(
        name="findAll",
        query="SELECT c FROM Steward c")
public class Steward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String surname;
    private String firstName;
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
