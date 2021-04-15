package com.company.crossInterfaces;

public interface AccountEntity {
    int getId();

    String getFullName();

    String getEmail();

    int getAccessLevel();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setMiddleName(String middleName);

    void setEmail(String email);

    void setAccessLevel(int accessLevel);
}
