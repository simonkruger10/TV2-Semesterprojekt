package com.company.common;

public interface IAccount {
    String getUUID();

    String getFullName();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    String getEmail();

    AccessLevel getAccessLevel();
}
