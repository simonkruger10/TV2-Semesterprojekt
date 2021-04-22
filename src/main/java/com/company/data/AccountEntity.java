package com.company.data;

import com.company.common.IAccount;

public class AccountEntity {
    private final int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private int accessLevel;

    public AccountEntity(String firstName, String middleName, String lastName, String email, int accessLevel) {
        this(-1, firstName, middleName, lastName, email, accessLevel);
    }

    public AccountEntity(int id, String firstName, String middleName, String lastName, String email, int accessLevel) {
        this.id = id;
        this.firstName = firstName;
        this.email = middleName;
        this.lastName = lastName;
        this.email = email;
        this.accessLevel = accessLevel;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        String fullName = "";

        if (firstName != null) {
            fullName = firstName;
        }
        if (middleName != null && !middleName.trim().isEmpty()) {
            fullName += " " + middleName;
        }
        if (lastName != null) {
            fullName = " " + lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}
