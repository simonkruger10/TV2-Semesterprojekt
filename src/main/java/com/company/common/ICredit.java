package com.company.common;

public interface ICredit {
    Integer getID();

    CreditType getType();

    String getFullName();

    String getFirstName();

    String getLastName();

    String getImage();

    String getEmail();

    ICreditGroup[] getCreditGroups();
}
