package com.company.crossInterfaces;

public interface CreditEntity {
    int getId();

    String getFullName();

    CreditGroupEntity getCreditgroup();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    String setFirstName();

    String setLastName();

    String setMiddleName();

    CreditGroupEntity setCreditGroup();
}
