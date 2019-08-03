package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class MissingPerson {
    @Id
    @GeneratedValue
    private Long id;

    private String firstname;
    private String lastname;
    private byte[] picture;

    public MissingPerson() {}

    public MissingPerson(Long id, String firstname, String lastname, byte[] picture) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissingPerson that = (MissingPerson) o;
        return id.equals(that.id) &&
                firstname.equals(that.firstname) &&
                lastname.equals(that.lastname) &&
                Arrays.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, firstname, lastname);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @Override
    public String toString() {
        return "MissingPerson{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }
}
