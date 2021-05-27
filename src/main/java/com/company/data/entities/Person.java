package com.company.data.entities;

import com.company.common.Tools;

public abstract class Person extends Identifier {
    private String firstName = null;
    private String lastName = null;
    private String email = null;

    public String getFullName() {
        return Tools.createFullName(firstName, lastName);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
