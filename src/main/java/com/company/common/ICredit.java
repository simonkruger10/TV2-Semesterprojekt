package com.company.common;

public interface ICredit {
    Integer getID();

    CreditType getType();

    String getFullName();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    String getImage();

    String getName();

    ICreditGroup getCreditGroup();
}
