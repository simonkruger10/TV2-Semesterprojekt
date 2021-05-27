package com.company.common;

public interface ICredit {
    Integer getID();

    CreditType getType();

    String getFullName();

    String getFirstName();

    String getLastName();

    String getName();

    String getImage();

    String getEmail();

    ICreditGroup[] getCreditGroups();
}
