package com.company.domain;

public class PersonDTO {
    private String firstName;
    private String middleName;
    private String lastName;

    public PersonDTO(String firstName) {
        this(firstName, null, null);
    }

    public PersonDTO(String firstName, String lastName) {
        this(firstName, lastName, null);
    }

    public PersonDTO(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFullName() {
        String fullName = firstName;

        if (middleName != null) {
            fullName += " " + middleName;
        }
        if (lastName != null) {
            fullName += " " + lastName;
        }

        return fullName;
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
