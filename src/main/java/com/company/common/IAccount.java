package com.company.common;

public interface IAccount {
    String getFullName();

    String getFirstName();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    String getLastName();

    void setMiddleName(String middleName);

    String getMiddleName();

    String getEmail();

    void setEmail(String email);

    IAccessLevel getAccessLevel();

    void setAccessLevel(IAccessLevel accessLevel);
}
