package com.company.domain;

import com.company.common.Tools;

public class PersonDTO extends MainDTO {
    private String firstName = null;
    private String middleName = null;
    private String lastName = null;

    public String getFullName() {
        return Tools.createFullName(firstName, middleName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
