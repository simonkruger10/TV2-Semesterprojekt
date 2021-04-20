package com.company.common;

public interface ICredit {
    int getId();

    String getFullName();

    ICreditGroup getCreditGroup();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setMiddleName(String middleName);

    void setCreditGroup(ICreditGroup creditGroup);
}
