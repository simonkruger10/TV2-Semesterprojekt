package com.company.common;

public interface ICredit {
    String getUUID();

    String getFullName();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    ICreditGroup getCreditGroup();
}
