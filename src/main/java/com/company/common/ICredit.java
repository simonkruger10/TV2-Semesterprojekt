package com.company.common;

public interface ICredit {
    String getFullName();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getMiddleName();

    void setMiddleName(String middleName);

    ICreditGroup getCreditGroup();

    void setCreditGroup(ICreditGroup creditGroup);
}
