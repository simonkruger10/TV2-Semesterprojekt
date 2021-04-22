package com.company.domain;

public class PersonDTO {
    private String firstName = null;
    private String middleName = null;
    private String lastName = null;

    public String getFullName() {
        String fullName = "";

        if (firstName != null) {
            fullName += firstName;
        }
        if (middleName != null && middleName.trim().isEmpty()) {
            fullName += " " + middleName;
        }
        if (lastName != null) {
            fullName += " " + lastName;
        }

        return fullName.trim();
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
