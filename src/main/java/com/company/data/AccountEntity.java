package com.company.data;

import com.company.common.IAccessLevel;

public class AccountEntity implements com.company.common.IAccount {
    private final int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private IAccessLevel accessLevel;

    public AccountEntity(String firstName, String middleName, String lastName, String email, IAccessLevel accessLevel) {
        this(-1, firstName, middleName, lastName, email, accessLevel);
    }

    public AccountEntity(int id, String firstName, String middleName, String lastName, String email, IAccessLevel accessLevel) {
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

    @Override
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

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public IAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(IAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
