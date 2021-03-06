package com.erofeev.st.alexei.myonlineshop.service.model;

import com.erofeev.st.alexei.myonlineshop.repository.model.Role;

public class UserRegistrationDTO {
    private String email;
    private String password;
    private String repeatedPassword;
    private String firstName;
    private String lastName;
    private String role;

    public UserRegistrationDTO(String email, String password, String repeatedPassword, String firstName, String lastName, String role) {
        this.email = email;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
