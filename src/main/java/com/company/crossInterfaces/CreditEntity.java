package com.company.crossInterfaces;

public interface CreditEntity {
    int getId();

    String getFullName();

    CreditGroupEntity getCreditGroup();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setMiddleName(String middleName);

    void setCreditGroup(CreditGroupEntity creditGroup);
}
