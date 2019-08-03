package be.hackinthewoods.childfocus.backend.controller.website.model;

import java.util.Objects;

public class RegisteringUser {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String password;
    private String passwordConfirm;

    public RegisteringUser() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteringUser that = (RegisteringUser) o;
        return Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                email.equals(that.email) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                password.equals(that.password) &&
                passwordConfirm.equals(that.passwordConfirm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, email, phoneNumber, password, passwordConfirm);
    }

    @Override
    public String toString() {
        return "RegisteringUser{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
