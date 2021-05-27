package com.company.common;

public interface ICredit {
    Integer getID();

    CreditType getType();

    String getFullName();

    String getFirstName();

    String getLastName();

    String getImage();

    String getName();

    String getEmail();

    ICreditGroup[] getCreditGroups();
}
