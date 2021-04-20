package com.company.data;

public class AccountEntity implements com.company.crossInterfaces.IAccount {
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

    @Override
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

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getAccessLevel() {
        return accessLevel;
    }

    @Override
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}
